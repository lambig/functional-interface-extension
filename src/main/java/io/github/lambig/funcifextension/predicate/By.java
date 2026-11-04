package io.github.lambig.funcifextension.predicate;

import java.util.function.Function;
import java.util.function.Predicate;

/** 値から取り出した要素に述語を適用する。 */
public class By {
  private By() {}

  /**
   * extractorで要素を取り出し、その結果を検査するための組立て関数を返す。
   * extractorや述語の評価は、完成した述語のtest呼出し時に行う。
   * @param <O> 元の値の型
   * @param <F> 取り出す要素の型
   * @param extractor 要素を取り出す関数。nullは評価時にNullPointerException
   * @return 要素の述語を受け取る組立て関数
   */
  public static <O, F> That<O, F> having(Function<O, F> extractor) {
    return fPredicate -> o -> fPredicate.test(extractor.apply(o));
  }

  /**
   * 要素の述語を元の値の述語へ変換する関数。
   * @param <A> 元の値の型
   * @param <B> 要素の型
   */
  public interface That<A, B> extends Function<Predicate<B>, Predicate<A>> {

    /**
     * {@link #that(Predicate)}へ委譲する。
     * @param original 要素の述語
     * @return 元の値の述語
     */
    @Override
    default Predicate<A> apply(Predicate<B> original) {
      return this.that(original);
    }

    /**
     * 要素の述語を指定する。
     * havingで生成した組立て関数では、抽出結果のnullをそのまま渡し、例外を伝播する。
     * @param bPredicate 要素の述語。havingの結果へnullを渡すとtest時にNullPointerException
     * @return 元の値に適用する述語
     */
    Predicate<A> that(Predicate<B> bPredicate);

    /**
     * 要素を{@link Equals#to(Object)}の深い等価比較で検査する。
     * @param target 比較対象（null可）
     * @return 元の値に適用する述語
     */
    default Predicate<A> thatEqualsTo(B target) {
      return this.that(Equals.to(target));
    }

  }
}


