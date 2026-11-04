package io.github.lambig.funcifextension.proposition;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 対象を捕捉した遅延判定。test/getの呼出しごとに再評価し、結果をキャッシュしない。
 * 対象は参照として保持するため、可変対象の変更は後の評価へ反映される。
 * スレッド安全性は捕捉した対象と述語に依存する。
 */
@FunctionalInterface
public interface Proposition extends Supplier<Boolean> {
  /**
   * 捕捉した対象を現在の状態で評価する。
   * @return 現時点で命題が成立する場合true。評価中の例外は呼出側へ伝播する
   */
  boolean test();

  /**
   * 引数順に評価し、最初のfalseでfalseを返す命題を組み立てる。
   * 配列をコピーせず保持するため、組立て後は配列を変更しないこと。
   * @param propositions 一つ以上の命題。null要素はその要素を評価した時点で例外になる
   * @return 遅延評価される合成命題
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
   * 引数順に評価し、最初のtrueでtrueを返す命題を組み立てる。
   * 配列をコピーせず保持するため、組立て後は配列を変更しないこと。
   * @param propositions 一つ以上の命題。null要素はその要素を評価した時点で例外になる
   * @return 遅延評価される合成命題
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
   * 述語を先に指定し、後から対象を指定する。
   * @param <E> 対象の型
   * @param predicate 判定する述語。nullは完成した命題の評価時にNullPointerException
   * @return 対象を受け取る組立て関数
   */
  static <E> PropositionBuilderFromPredicate<E> predicate(Predicate<E> predicate) {
    return subject -> () -> predicate.test(subject);
  }

  /**
   * 対象を先に指定し、後から述語を指定する。
   * @param <E> 対象の型
   * @param subject 対象（null可）。評価時に述語へそのまま渡す
   * @return 述語を受け取る組立て関数
   */
  static <E> PropositionBuilderFromSubject<E> subject(E subject) {
    return predicate -> () -> predicate.test(subject);
  }

  /** @return test()をその都度呼び出した結果。キャッシュしない */
  @Override
  default Boolean get() {
    return this.test();
  }

  /**
   * 渡された入力を使用せず、この命題をその都度評価する述語に変換する。
   * @param <E> 返す述語の入力型
   * @return この命題を評価する述語（入力がnullでも同じ評価を行う）
   */
  default <E> Predicate<E> toPredicate() {
    return anything -> this.test();
  }

  /**
   * この命題とnextをこの順に論理積で合成する。
   * @param next 次の命題。短絡されず評価されたときにnullならNullPointerException
   * @return 静的andと同じ短絡評価をする命題
   */
  default Proposition and(Proposition next) {
    return and(this, next);
  }

  /**
   * この命題とnextをこの順に論理和で合成する。
   * @param next 次の命題。短絡されず評価されたときにnullならNullPointerException
   * @return 静的orと同じ短絡評価をする命題
   */
  default Proposition or(Proposition next) {
    return or(this, next);
  }

  /**
   * 述語に対象を結び付ける組立て関数。
   * @param <S> 対象の型
   */
  interface PropositionBuilderFromPredicate<S> extends Function<S, Proposition> {
    /**
     * 述語に対象を結び付ける。
     * @param subject 対象。predicateで生成した組立て関数はnullをそのまま述語へ渡す
     * @return 対象を捕捉した遅延命題
     */
    Proposition satisfiedBy(S subject);

    /**
     * satisfiedByへ委譲する。
     * @param s 対象
     * @return 対象を捕捉した命題
     */
    @Override
    default Proposition apply(S s) {
      return this.satisfiedBy(s);
    }
  }

  /**
   * 対象に述語を結び付ける組立て関数。
   * @param <S> 対象の型
   */
  interface PropositionBuilderFromSubject<S> extends Function<Predicate<S>, Proposition> {
    /**
     * 対象に述語を結び付ける。
     * @param predicate 述語。subjectで生成した組立て関数ではnullは命題の評価時に例外
     * @return 対象を捕捉した遅延命題
     */
    Proposition satisfies(Predicate<S> predicate);

    /**
     * satisfiesへ委譲する。
     * @param s 述語
     * @return 対象を捕捉した命題
     */
    @Override
    default Proposition apply(Predicate<S> s) {
      return this.satisfies(s);
    }
  }
}
