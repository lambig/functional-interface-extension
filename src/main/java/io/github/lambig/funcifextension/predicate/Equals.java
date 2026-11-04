package io.github.lambig.funcifextension.predicate;

import java.util.Objects;
import java.util.function.Predicate;

/** 指定した値と等しいかを判定する述語を作る。配列は要素の内容で比較する。 */
public class Equals {
  private Equals() {}
  /**
   * 判定対象がtargetと等しい、という述語を返す。
   * 両値がnullならtrue、一方だけがnullならfalse。配列は要素を深く比較する。
   * 両値が配列なら、長さと対応する要素を比較し、入れ子の配列も同様に比較する。
   * その他の非null値では、判定対象のequalsによる等価性を用いる。
   * 例えば{@code Equals.to(new int[] {1, 2})}は、同じ順序で同じ要素を持つ別の配列にもtrueを返す。
   * targetの内容が変わると、後の判定結果も変わり得る。
   * @param <O> 比較する値の型
   * @param target 比較対象（null可）
   * @return 上記の基準でtargetと等しい場合true、等しくない場合falseを返す述語
   * @see Objects#deepEquals(Object, Object)
   */
  public static <O> Predicate<O> to(O target) {
    return evaluated -> Objects.deepEquals(evaluated, target);
  }
}
