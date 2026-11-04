package io.github.lambig.funcifextension.predicate;

/**
 * 三つの入力の組が満たすべき条件を表す述語。
 * 例えば単価・数量・値引額の組が、注文を受け付ける条件を満たすかを表せる。
 * 具体的な条件、nullの許容、例外は、利用者が与える述語の契約による。
 * @param <A> 第一引数の型
 * @param <B> 第二引数の型
 * @param <C> 第三引数の型
 */
@FunctionalInterface
public interface TriPredicate<A, B, C> {
  /**
   * 入力a・b・cの組が、この述語の条件を満たすかを返す。
   * @param a 第一引数
   * @param b 第二引数
   * @param c 第三引数
   * @return 条件を満たす場合true、満たさない場合false
   */
  boolean test(A a, B b, C c);
}
