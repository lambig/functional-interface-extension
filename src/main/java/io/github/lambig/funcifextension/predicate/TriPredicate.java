package io.github.lambig.funcifextension.predicate;

/**
 * 三つの引数を判定する述語。nullの許容と例外は実装が定める。
 * @param <A> 入力の型
 * @param <B> 入力の型
 * @param <C> 入力の型
 */
@FunctionalInterface
public interface TriPredicate<A, B, C> {
  /**
   * 三つの入力を評価する。
   * @param a 第一引数
   * @param b 第二引数
   * @param c 第三引数
   * @return 条件を満たす場合true
   */
  boolean test(A a, B b, C c);
}
