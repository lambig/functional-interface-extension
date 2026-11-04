package io.github.lambig.funcifextension.proposition;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 真か偽かを判定できる「命題」を表す。
 * {@link Predicate}（述語）が「xは正の整数である」のような対象xについての条件を表すのに対し、
 * Propositionは「3は正の整数である」のように対象が定まった主張を表す。
 * 述語には{@code test(x)}で対象を渡し、命題には引数なしの{@link #test()}で真偽を問い合わせる。
 *
 * <pre>{@code
 * Predicate<Integer> positive = n -> n > 0; // xは正の整数である
 * Proposition threeIsPositive = Proposition.subject(3).satisfies(positive);
 * boolean result = threeIsPositive.test(); // 「3は正の整数である」はtrue
 * }</pre>
 *
 * <p>対象と述語から命題を作るほか、ラムダ式で命題を直接表したり、
 * {@link #and(Proposition)}・{@link #or(Proposition)}で「かつ」「または」の主張を作ったりできる。
 * 条件の対象を決める場所と、その真偽を問い合わせる場所を分けたいときに使う。
 *
 * <p>このAPIは判定時点での真偽を返す。例えば「この注文は未払いである」という命題は、
 * 注文の状態が変われば結果も変わり得る。作成時点の真偽を保存する値ではない。
 * 複数スレッドから利用する場合、対象と述語がその利用方法に対応している必要がある。
 */
@FunctionalInterface
public interface Proposition extends Supplier<Boolean> {
  /**
   * この命題が成り立つかを判定する。
   * 判定中の実行時例外は呼出側へ伝わる。
   * @return 判定時点でこの命題が成り立つ場合true、成り立たない場合false
   */
  boolean test();

  /**
   * 指定した命題がすべて成り立つ、という命題（論理積）を返す。
   * 判定は引数順に行い、falseになった後の命題は判定しない。
   * 作成後は引数の配列を変更しないこと。
   * @param propositions 一つ以上の命題。null要素はその要素の判定時にNullPointerExceptionとなる
   * @return 全命題が成り立つ場合true、一つでも成り立たない場合falseとなる命題
   * @throws IllegalArgumentException 配列が空の場合
   * @throws NullPointerException 配列がnullの場合（組立て時）
   */
  static Proposition and(Proposition... propositions) {
    if (propositions.length == 0) {
      throw new IllegalArgumentException("At least 1 proposition needed");
    }
    return () -> Arrays.stream(propositions).allMatch(Proposition::test);
  }

  /**
   * 指定した命題の少なくとも一つが成り立つ、という命題（論理和）を返す。
   * 判定は引数順に行い、trueになった後の命題は判定しない。
   * 作成後は引数の配列を変更しないこと。
   * @param propositions 一つ以上の命題。null要素はその要素の判定時にNullPointerExceptionとなる
   * @return 一つ以上が成り立つ場合true、どれも成り立たない場合falseとなる命題
   * @throws IllegalArgumentException 配列が空の場合
   * @throws NullPointerException 配列がnullの場合（組立て時）
   */
  static Proposition or(Proposition... propositions) {
    if (propositions.length == 0) {
      throw new IllegalArgumentException("At least 1 proposition needed");
    }
    return () -> Arrays.stream(propositions).anyMatch(Proposition::test);
  }

  /**
   * 「xはこの条件を満たす」という述語から、対象を指定して命題を作れるようにする。
   * 例えば{@code predicate(positive).satisfiedBy(3)}は「3は正である」を表す。
   * @param <E> 対象の型
   * @param predicate 判定する述語。nullは完成した命題の評価時にNullPointerException
   * @return 対象を指定すると、その対象が述語を満たすという命題を返す関数
   */
  static <E> PropositionBuilderFromPredicate<E> predicate(Predicate<E> predicate) {
    return subject -> () -> predicate.test(subject);
  }

  /**
   * 指定した対象について、条件を選んで命題を作れるようにする。
   * 例えば{@code subject(3).satisfies(positive)}は「3は正である」を表す。
   * @param <E> 対象の型
   * @param subject 対象（null可）。評価時に述語へそのまま渡す
   * @return 述語を指定すると、この対象がその述語を満たすという命題を返す関数
   */
  static <E> PropositionBuilderFromSubject<E> subject(E subject) {
    return predicate -> () -> predicate.test(subject);
  }

  /**
   * 命題の真偽を{@link Supplier}として取得する。
   * @return 判定時点でこの命題が成り立つ場合Boolean.TRUE、成り立たない場合Boolean.FALSE
   */
  @Override
  default Boolean get() {
    return this.test();
  }

  /**
   * この命題の真偽を、入力によらない条件として利用できる述語を返す。
   * 命題が成り立つときはどの入力にもtrue、成り立たないときはどの入力にもfalseを返す。
   * 対象を入力に置き換える操作ではなく、元の命題について判定する。
   * @param <E> 返す述語の入力型
   * @return 入力に関係なく、判定時点のこの命題の真偽を返す述語（null入力も同様）
   */
  default <E> Predicate<E> toPredicate() {
    return anything -> this.test();
  }

  /**
   * この命題とnextがともに成り立つ、という命題を返す。
   * この命題がfalseならnextは判定しない。
   * @param next 次の命題。短絡されず評価されたときにnullならNullPointerException
   * @return 二つの命題の論理積
   */
  default Proposition and(Proposition next) {
    return and(this, next);
  }

  /**
   * この命題とnextの少なくとも一方が成り立つ、という命題を返す。
   * この命題がtrueならnextは判定しない。
   * @param next 次の命題。短絡されず評価されたときにnullならNullPointerException
   * @return 二つの命題の論理和
   */
  default Proposition or(Proposition next) {
    return or(this, next);
  }

  /**
   * 一つの述語について、対象ごとの命題を作る関数。
   * @param <S> 対象の型
   */
  interface PropositionBuilderFromPredicate<S> extends Function<S, Proposition> {
    /**
     * subjectがこの述語を満たす、という命題を返す。
     * @param subject 対象。predicateで生成した組立て関数はnullをそのまま述語へ渡す
     * @return 指定した対象についての命題。真偽はtest/getで判定する
     */
    Proposition satisfiedBy(S subject);

    /**
     * sがこの述語を満たす、という命題を返す。
     * {@link #satisfiedBy(Object)}と同じ結果を、Functionとして利用できる。
     * @param s 対象
     * @return 指定した対象についての命題
     */
    @Override
    default Proposition apply(S s) {
      return this.satisfiedBy(s);
    }
  }

  /**
   * 一つの対象について、条件ごとの命題を作る関数。
   * @param <S> 対象の型
   */
  interface PropositionBuilderFromSubject<S> extends Function<Predicate<S>, Proposition> {
    /**
     * この対象がpredicateを満たす、という命題を返す。
     * @param predicate 述語。subjectで生成した関数へnullを渡すと、命題のtest/get時にNullPointerExceptionとなる
     * @return 指定した条件についての命題。真偽はtest/getで判定する
     */
    Proposition satisfies(Predicate<S> predicate);

    /**
     * この対象が述語sを満たす、という命題を返す。
     * {@link #satisfies(Predicate)}と同じ結果を、Functionとして利用できる。
     * @param s 述語
     * @return 指定した条件についての命題
     */
    @Override
    default Proposition apply(Predicate<S> s) {
      return this.satisfies(s);
    }
  }
}
