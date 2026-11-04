package io.github.lambig.funcifextension.predicate;

import java.util.Objects;
import java.util.function.Predicate;

/** 一つの対象について、複数の条件の「すべて」または「いずれか」を満たすかを判定する述語を作る。 */
public class Predicates {
  private Predicates() {}
  /**
   * 対象が指定した条件をすべて満たす、という述語（論理積）を返す。
   * 呼出後に元の配列の要素を変更しても、判定に使う条件の組合せは変わらない。
   * 条件は引数順に判定し、falseになった後の条件は判定しない。
   * 判定中の例外は呼出側へ伝わり、後の条件は判定しない。
   * @param <E> 入力の型
   * @param predicate 一つ以上のnullでない述語。入力値のnull許容は各述語による
   * @return 全条件を満たす場合true、一つでも満たさない場合falseを返す述語
   * @throws IllegalArgumentException 要素がゼロの場合
   * @throws NullPointerException 配列または要素がnullの場合（合成時）
   */
  @SafeVarargs
  public static <E> Predicate<E> and(Predicate<E>... predicate) {
    if (predicate.length == 0) {
      throw new IllegalArgumentException("At least 1 predicate needed");
    }
    Predicate<E>[] operands = predicate.clone();
    for (Predicate<E> operand : operands) {
      Objects.requireNonNull(operand);
    }
    if (operands.length == 1) {
      return operands[0];
    }
    return value -> {
      for (Predicate<E> operand : operands) {
        if (!operand.test(value)) {
          return false;
        }
      }
      return true;
    };
  }

  /**
   * 対象が指定した条件の少なくとも一つを満たす、という述語（論理和）を返す。
   * 呼出後に元の配列の要素を変更しても、判定に使う条件の組合せは変わらない。
   * 条件は引数順に判定し、trueになった後の条件は判定しない。
   * 判定中の例外は呼出側へ伝わり、後の条件は判定しない。
   * @param <E> 入力の型
   * @param predicate 一つ以上のnullでない述語。入力値のnull許容は各述語による
   * @return 一つ以上の条件を満たす場合true、どれも満たさない場合falseを返す述語
   * @throws IllegalArgumentException 要素がゼロの場合
   * @throws NullPointerException 配列または要素がnullの場合（合成時）
   */
  @SafeVarargs
  public static <E> Predicate<E> or(Predicate<E>... predicate) {
    if (predicate.length == 0) {
      throw new IllegalArgumentException("At least 1 predicate needed");
    }
    Predicate<E>[] operands = predicate.clone();
    for (Predicate<E> operand : operands) {
      Objects.requireNonNull(operand);
    }
    if (operands.length == 1) {
      return operands[0];
    }
    return value -> {
      for (Predicate<E> operand : operands) {
        if (operand.test(value)) {
          return true;
        }
      }
      return false;
    };
  }
}
