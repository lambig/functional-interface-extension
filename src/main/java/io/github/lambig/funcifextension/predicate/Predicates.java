package io.github.lambig.funcifextension.predicate;

import java.util.Arrays;
import java.util.function.Predicate;

/** JDKの述語を左から短絡評価する複合述語を作る。 */
public class Predicates {
  private Predicates() {}
  /**
   * 述語を引数順に評価し、最初のfalseでfalseを返す。
   * 合成時には述語を実行しない。評価時の例外は伝播し、後続を評価しない。
   * @param <E> 入力の型
   * @param predicate 一つ以上のnullでない述語。入力値のnull許容は各述語による
   * @return 合成した述語。一つだけの場合はその述語自体
   * @throws IllegalArgumentException 要素がゼロの場合
   * @throws NullPointerException 配列または要素がnullの場合（合成時）
   */
  @SafeVarargs
  public static <E> Predicate<E> and(Predicate<E>... predicate) {
    return
        Arrays
            .stream(predicate)
            .reduce(Predicate::and)
            .orElseThrow(() -> new IllegalArgumentException("At least 1 predicate needed"));
  }

  /**
   * 述語を引数順に評価し、最初のtrueでtrueを返す。
   * 合成時には述語を実行しない。評価時の例外は伝播し、後続を評価しない。
   * @param <E> 入力の型
   * @param predicate 一つ以上のnullでない述語。入力値のnull許容は各述語による
   * @return 合成した述語。一つだけの場合はその述語自体
   * @throws IllegalArgumentException 要素がゼロの場合
   * @throws NullPointerException 配列または要素がnullの場合（合成時）
   */
  @SafeVarargs
  public static <E> Predicate<E> or(Predicate<E>... predicate) {
    return
        Arrays
            .stream(predicate)
            .reduce(Predicate::or)
            .orElseThrow(() -> new IllegalArgumentException("At least 1 predicate needed"));
  }
}
