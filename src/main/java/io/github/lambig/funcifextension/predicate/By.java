package io.github.lambig.funcifextension.predicate;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 対象の特徴に着目した条件を表す。
 * 例えば{@code By.having(String::length).thatEqualsTo(3)}は「文字列の長さが3である」という述語になる。
 */
public class By {
  private By() {}

  /**
   * 条件に使う対象の特徴を指定する。
   * 例えば文字列の長さを指定すれば、その長さについての条件から文字列の述語を作れる。
   * @param <O> 元の値の型
   * @param <F> 取り出す要素の型
   * @param extractor 要素を取り出す関数。nullは評価時にNullPointerException
   * @return 特徴についての条件を指定すると、元の対象についての述語を返す関数
   */
  public static <O, F> That<O, F> having(Function<O, F> extractor) {
    return fPredicate -> o -> fPredicate.test(extractor.apply(o));
  }

  /**
   * 特徴についての条件から、元の対象についての述語を作る関数。
   * @param <A> 元の値の型
   * @param <B> 要素の型
   */
  public interface That<A, B> extends Function<Predicate<B>, Predicate<A>> {

    /**
     * {@link #that(Predicate)}と同じ述語を、Functionの操作名で作る。
     * @param original 要素の述語
     * @return 元の値の述語
     */
    @Override
    default Predicate<A> apply(Predicate<B> original) {
      return this.that(original);
    }

    /**
     * 対象の特徴がbPredicateを満たす、という述語を返す。
     * havingで生成した組立て関数では、抽出結果のnullをそのまま渡し、例外を伝播する。
     * @param bPredicate 要素の述語。havingの結果へnullを渡すとtest時にNullPointerException
     * @return 元の値に適用する述語
     */
    Predicate<A> that(Predicate<B> bPredicate);

    /**
     * 対象の特徴がtargetと等しい、という述語を返す。
     * 配列やnullの比較は{@link Equals#to(Object)}と同じ条件になる。
     * @param target 比較対象（null可）
     * @return 元の値に適用する述語
     */
    default Predicate<A> thatEqualsTo(B target) {
      return this.that(Equals.to(target));
    }

  }
}


