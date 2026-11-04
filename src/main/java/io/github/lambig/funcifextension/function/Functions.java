package io.github.lambig.funcifextension.function;

import java.util.function.Function;

/**
 * 複数の値変換を、一つの入力から最終結果を求める関数にする。
 * {@code sequenceOf(f, g)}はg(f(x))、{@code compositionOf(f, g)}はf(g(x))となる関数を返す。
 * 例えば文字列の空白除去と整数への変換をつなげば、「数量を表す文字列を整数にする」関数になる。
 */
public class Functions {
  private Functions() {}
  /**
   * 入力xに対して{@code function2(function1(x))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 返す関数の結果の型
   * @param function1 A型の値からB型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @return A型の入力をC型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C> Function<A, C> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2) {
    return function1.andThen(function2);
  }

  /**
   * 入力xに対して{@code function3(function2(function1(x)))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 返す関数の結果の型
   * @param function1 A型の値からB型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @param function3 C型の値からD型の値を求める、nullでない関数
   * @return A型の入力をD型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D> Function<A, D> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3) {
    return sequenceOf(function1, function2).andThen(function3);
  }

  /**
   * 入力xに対して{@code function4(function3(function2(function1(x))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 返す関数の結果の型
   * @param function1 A型の値からB型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @param function3 C型の値からD型の値を求める、nullでない関数
   * @param function4 D型の値からE型の値を求める、nullでない関数
   * @return A型の入力をE型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E> Function<A, E> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4) {
    return sequenceOf(function1, function2, function3).andThen(function4);
  }

  /**
   * 入力xに対して{@code function5(function4(function3(function2(function1(x)))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 4段階の変換を終えた値の型
   * @param <F> 返す関数の結果の型
   * @param function1 A型の値からB型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @param function3 C型の値からD型の値を求める、nullでない関数
   * @param function4 D型の値からE型の値を求める、nullでない関数
   * @param function5 E型の値からF型の値を求める、nullでない関数
   * @return A型の入力をF型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E, F> Function<A, F> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4,
      Function<? super E, ? extends F> function5) {
    return sequenceOf(function1, function2, function3, function4).andThen(function5);
  }

  /**
   * 入力xに対して{@code function6(function5(function4(function3(function2(function1(x))))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 4段階の変換を終えた値の型
   * @param <F> 5段階の変換を終えた値の型
   * @param <G> 返す関数の結果の型
   * @param function1 A型の値からB型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @param function3 C型の値からD型の値を求める、nullでない関数
   * @param function4 D型の値からE型の値を求める、nullでない関数
   * @param function5 E型の値からF型の値を求める、nullでない関数
   * @param function6 F型の値からG型の値を求める、nullでない関数
   * @return A型の入力をG型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E, F, G> Function<A, G> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4,
      Function<? super E, ? extends F> function5,
      Function<? super F, ? extends G> function6) {
    return sequenceOf(function1, function2, function3, function4, function5).andThen(function6);
  }

  /**
   * 入力xに対して{@code function7(function6(function5(function4(function3(function2(function1(x)))))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 4段階の変換を終えた値の型
   * @param <F> 5段階の変換を終えた値の型
   * @param <G> 6段階の変換を終えた値の型
   * @param <H> 返す関数の結果の型
   * @param function1 A型の値からB型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @param function3 C型の値からD型の値を求める、nullでない関数
   * @param function4 D型の値からE型の値を求める、nullでない関数
   * @param function5 E型の値からF型の値を求める、nullでない関数
   * @param function6 F型の値からG型の値を求める、nullでない関数
   * @param function7 G型の値からH型の値を求める、nullでない関数
   * @return A型の入力をH型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E, F, G, H> Function<A, H> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4,
      Function<? super E, ? extends F> function5,
      Function<? super F, ? extends G> function6,
      Function<? super G, ? extends H> function7) {
    return sequenceOf(function1, function2, function3, function4, function5, function6).andThen(function7);
  }

  /**
   * 入力xに対して{@code function1(function2(x))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 返す関数の結果の型
   * @param function1 B型の値からC型の値を求める、nullでない関数
   * @param function2 A型の値からB型の値を求める、nullでない関数
   * @return A型の入力をC型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C> Function<A, C> compositionOf(
      Function<? super B, C> function1,
      Function<? super A, ? extends B> function2) {
    return function1.compose(function2);
  }

  /**
   * 入力xに対して{@code function1(function2(function3(x)))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 返す関数の結果の型
   * @param function1 C型の値からD型の値を求める、nullでない関数
   * @param function2 B型の値からC型の値を求める、nullでない関数
   * @param function3 A型の値からB型の値を求める、nullでない関数
   * @return A型の入力をD型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D> Function<A, D> compositionOf(
      Function<? super C, D> function1,
      Function<? super B, ? extends C> function2,
      Function<? super A, ? extends B> function3) {
    return compositionOf(function1, function2).compose(function3);
  }

  /**
   * 入力xに対して{@code function1(function2(function3(function4(x))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 返す関数の結果の型
   * @param function1 D型の値からE型の値を求める、nullでない関数
   * @param function2 C型の値からD型の値を求める、nullでない関数
   * @param function3 B型の値からC型の値を求める、nullでない関数
   * @param function4 A型の値からB型の値を求める、nullでない関数
   * @return A型の入力をE型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E> Function<A, E> compositionOf(
      Function<? super D, E> function1,
      Function<? super C, ? extends D> function2,
      Function<? super B, ? extends C> function3,
      Function<? super A, ? extends B> function4) {
    return compositionOf(function1, function2, function3).compose(function4);
  }

  /**
   * 入力xに対して{@code function1(function2(function3(function4(function5(x)))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 4段階の変換を終えた値の型
   * @param <F> 返す関数の結果の型
   * @param function1 E型の値からF型の値を求める、nullでない関数
   * @param function2 D型の値からE型の値を求める、nullでない関数
   * @param function3 C型の値からD型の値を求める、nullでない関数
   * @param function4 B型の値からC型の値を求める、nullでない関数
   * @param function5 A型の値からB型の値を求める、nullでない関数
   * @return A型の入力をF型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E, F> Function<A, F> compositionOf(
      Function<? super E, F> function1,
      Function<? super D, ? extends E> function2,
      Function<? super C, ? extends D> function3,
      Function<? super B, ? extends C> function4,
      Function<? super A, ? extends B> function5) {
    return compositionOf(function1, function2, function3, function4).compose(function5);
  }

  /**
   * 入力xに対して{@code function1(function2(function3(function4(function5(function6(x))))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 4段階の変換を終えた値の型
   * @param <F> 5段階の変換を終えた値の型
   * @param <G> 返す関数の結果の型
   * @param function1 F型の値からG型の値を求める、nullでない関数
   * @param function2 E型の値からF型の値を求める、nullでない関数
   * @param function3 D型の値からE型の値を求める、nullでない関数
   * @param function4 C型の値からD型の値を求める、nullでない関数
   * @param function5 B型の値からC型の値を求める、nullでない関数
   * @param function6 A型の値からB型の値を求める、nullでない関数
   * @return A型の入力をG型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E, F, G> Function<A, G> compositionOf(
      Function<? super F, G> function1,
      Function<? super E, ? extends F> function2,
      Function<? super D, ? extends E> function3,
      Function<? super C, ? extends D> function4,
      Function<? super B, ? extends C> function5,
      Function<? super A, ? extends B> function6) {
    return compositionOf(function1, function2, function3, function4, function5).compose(function6);
  }

  /**
   * 入力xに対して{@code function1(function2(function3(function4(function5(function6(function7(x)))))))}を結果とする合成関数を返す。
   * <p>関数の適用をf(x)と表す。戻り値に入力を渡すと、その入力に対応する結果を得られる。
   * 入力値・中間値・結果のnull許容は各関数の契約による。
   * 結果を求める途中で例外が発生した場合は呼出側へ伝わり、後の関数は適用しない。
   * @param <A> 返す関数の入力の型
   * @param <B> 1段階の変換を終えた値の型
   * @param <C> 2段階の変換を終えた値の型
   * @param <D> 3段階の変換を終えた値の型
   * @param <E> 4段階の変換を終えた値の型
   * @param <F> 5段階の変換を終えた値の型
   * @param <G> 6段階の変換を終えた値の型
   * @param <H> 返す関数の結果の型
   * @param function1 G型の値からH型の値を求める、nullでない関数
   * @param function2 F型の値からG型の値を求める、nullでない関数
   * @param function3 E型の値からF型の値を求める、nullでない関数
   * @param function4 D型の値からE型の値を求める、nullでない関数
   * @param function5 C型の値からD型の値を求める、nullでない関数
   * @param function6 B型の値からC型の値を求める、nullでない関数
   * @param function7 A型の値からB型の値を求める、nullでない関数
   * @return A型の入力をH型の結果へ対応付ける関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D, E, F, G, H> Function<A, H> compositionOf(
      Function<? super G, H> function1,
      Function<? super F, ? extends G> function2,
      Function<? super E, ? extends F> function3,
      Function<? super D, ? extends E> function4,
      Function<? super C, ? extends D> function5,
      Function<? super B, ? extends C> function6,
      Function<? super A, ? extends B> function7) {
    return compositionOf(function1, function2, function3, function4, function5, function6).compose(function7);
  }

  /**
   * 指定した値について、後から選ぶ関数の結果を求められるようにする。
   * 例えば値を3に固定し、文字列化する関数を渡せば文字列の"3"を得られる。
   * <p>返された関数にnullを渡すと、その呼出しでNullPointerExceptionとなる。
   * 適用する関数が投げた実行時例外は呼出側へ伝わる。valueの状態が変われば結果も変わり得る。
   * @param <A> 入力の型
   * @param <B> 結果の型
   * @param value 関数に渡す値。nullもそのまま関数へ渡す
   * @return 関数fを受け取ると{@code f.apply(value)}の結果を返す関数
   */
  public static <A, B> Function<Function<? super A, ? extends B>, B> applyTo(A value) {
    return function -> function.apply(value);
  }
}
