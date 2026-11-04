package io.github.lambig.funcifextension.function;

/**
 * 三つの引数から値を求める関数。nullの許容と例外は実装が定める。
 * @param <A> 入力の型
 * @param <B> 入力の型
 * @param <C> 入力の型
 * @param <X> 結果の型
 */
@FunctionalInterface
public interface TriFunction<A, B, C, X> {
  /**
   * 三つの入力を評価する。
   * @param a 第一引数
   * @param b 第二引数
   * @param c 第三引数
   * @return 評価結果
   */
  X apply(A a, B b, C c);
}
