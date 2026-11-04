package io.github.lambig.funcifextension.function;

/**
 * 三つの入力の組に対して、一つの結果を対応付ける関数。
 * 例えば単価・数量・値引額から合計金額を求める計算を表せる。
 * 具体的な計算、入力・結果のnull許容、例外は、利用者が与える関数の契約による。
 * @param <A> 第一引数の型
 * @param <B> 第二引数の型
 * @param <C> 第三引数の型
 * @param <X> 結果の型
 */
@FunctionalInterface
public interface TriFunction<A, B, C, X> {
  /**
   * この関数が定める、入力a・b・cの組に対応する結果を返す。
   * @param a 第一引数
   * @param b 第二引数
   * @param c 第三引数
   * @return この関数の計算で得られるX型の値
   */
  X apply(A a, B b, C c);
}
