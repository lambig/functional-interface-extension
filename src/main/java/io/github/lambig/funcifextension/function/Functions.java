package io.github.lambig.funcifextension.function;

import java.util.function.Function;

/** JDKの関数を評価順が明示された一つの関数へ合成する。 */
public class Functions {
  private Functions() {}
  /**
   * 2個の関数を引数の左から右の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 3個の関数を引数の左から右の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 4個の関数を引数の左から右の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 5個の関数を引数の左から右の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 6個の関数を引数の左から右の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 7個の関数を引数の左から右の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 2個の関数を引数の右から左の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 3個の関数を引数の右から左の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 4個の関数を引数の右から左の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 5個の関数を引数の右から左の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 6個の関数を引数の右から左の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 7個の関数を引数の右から左の順に評価する関数を返す。
   * 実行は戻り値へのapply時まで遅延し、例外発生後の関数は呼ばない。
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
   * 値を捕捉し、後から渡された関数にその値を適用する。
   * @param <A> 入力の型
   * @param <B> 結果の型
   * @param value 捕捉する値。nullもそのまま関数へ渡す
   * @return 関数を受け取って評価する関数。nullの関数では評価時にNullPointerException
   */
  public static <A, B> Function<Function<? super A, ? extends B>, B> applyTo(A value) {
    return function -> function.apply(value);
  }
}
