package io.github.lambig.funcifextension.predicate;

import java.util.Objects;
import java.util.function.Predicate;

/** 配列を含む値の深い等価比較を述語にする。 */
public class Equals {
  private Equals() {}
  /**
   * Objects.deepEquals(評価対象, target)で比較する述語を返す。
   * 両値がnullならtrue、一方だけがnullならfalse。配列は要素を深く比較する。
   * targetをコピーせず保持するため、可変値の変更は後の比較に反映される。
   * @param <O> 比較する値の型
   * @param target 比較対象（null可）
   * @return 深い等価比較を行う述語
   */
  public static <O> Predicate<O> to(O target) {
    return evaluated -> Objects.deepEquals(evaluated, target);
  }
}
