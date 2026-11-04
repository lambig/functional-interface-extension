package io.github.lambig.funcifextension.predicate;

import java.util.Objects;
import java.util.function.Predicate;

/** 指定した値と等しいかを判定する述語を作る。配列は要素の内容で比較する。 */
public class Equals {
  private Equals() {}
  /**
   * 判定対象がtargetと等しい、という述語を返す。
   * 両値がnullならtrue、一方だけがnullならfalse。配列は要素を深く比較する。
   * 例えば{@code Equals.to(new int[] {1, 2})}は、同じ要素を持つ別の配列にもtrueを返す。
   * targetの内容が変わると、後の判定結果も変わり得る。
   * @param <O> 比較する値の型
   * @param target 比較対象（null可）
   * @return 深い等価比較を行う述語
   * @see Objects#deepEquals(Object, Object)
   */
  public static <O> Predicate<O> to(O target) {
    return evaluated -> Objects.deepEquals(evaluated, target);
  }
}
