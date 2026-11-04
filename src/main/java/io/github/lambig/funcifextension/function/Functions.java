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
   * 2個の変換を左から右につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C> Function<A, C> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2) {
    return function1.andThen(function2);
  }

  /**
   * 3個の変換を左から右につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D> Function<A, D> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3) {
    return sequenceOf(function1, function2).andThen(function3);
  }

  /**
   * 4個の変換を左から右につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 5個の変換を左から右につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param <F> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function5 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 6個の変換を左から右につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param <F> 合成の各段階で用いる値の型
   * @param <G> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function5 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function6 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 7個の変換を左から右につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param <F> 合成の各段階で用いる値の型
   * @param <G> 合成の各段階で用いる値の型
   * @param <H> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function5 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function6 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function7 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 2個の変換を右から左につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C> Function<A, C> compositionOf(
      Function<? super B, C> function1,
      Function<? super A, ? extends B> function2) {
    return function1.compose(function2);
  }

  /**
   * 3個の変換を右から左につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
   * @throws NullPointerException 関数引数がnullの場合（合成時）
   */
  public static <A, B, C, D> Function<A, D> compositionOf(
      Function<? super C, D> function1,
      Function<? super B, ? extends C> function2,
      Function<? super A, ? extends B> function3) {
    return compositionOf(function1, function2).compose(function3);
  }

  /**
   * 4個の変換を右から左につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 5個の変換を右から左につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param <F> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function5 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 6個の変換を右から左につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param <F> 合成の各段階で用いる値の型
   * @param <G> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function5 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function6 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * 7個の変換を右から左につなぎ、入力から最終結果を求める関数を返す。
   * 戻り値のapplyに入力を渡すと、変換結果を得られる。途中で例外が発生すると、後の変換は行わない。
   * @param <A> 合成の各段階で用いる値の型
   * @param <B> 合成の各段階で用いる値の型
   * @param <C> 合成の各段階で用いる値の型
   * @param <D> 合成の各段階で用いる値の型
   * @param <E> 合成の各段階で用いる値の型
   * @param <F> 合成の各段階で用いる値の型
   * @param <G> 合成の各段階で用いる値の型
   * @param <H> 合成の各段階で用いる値の型
   * @param function1 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function2 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function3 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function4 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function5 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function6 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @param function7 nullでない関数。入力値・中間値のnull許容は各関数の契約による
   * @return 合成した関数
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
   * @param <A> 入力の型
   * @param <B> 結果の型
   * @param value 関数に渡す値。nullもそのまま関数へ渡す
   * @return 関数を受け取って評価する関数。nullの関数では評価時にNullPointerException
   */
  public static <A, B> Function<Function<? super A, ? extends B>, B> applyTo(A value) {
    return function -> function.apply(value);
  }
}
