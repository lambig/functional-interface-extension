package io.github.lambig.funcifextension.function;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Functions {
  public static <A, B, C> Function<A, ? extends C> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2) {
    return function1.andThen(function2);
  }

  public static <A, B, C, D> Function<A, ? extends D> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3) {
    return sequenceOf(function1, function2).andThen(function3);
  }

  public static <A, B, C, D, E> Function<A, ? extends E> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4) {
    return sequenceOf(function1, function2, function3).andThen(function4);
  }

  public static <A, B, C, D, E, F> Function<A, ? extends F> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4,
      Function<? super E, ? extends F> function5) {
    return sequenceOf(function1, function2, function3, function4).andThen(function5);
  }

  public static <A, B, C, D, E, F, G> Function<A, ? extends G> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4,
      Function<? super E, ? extends F> function5,
      Function<? super F, ? extends G> function6) {
    return sequenceOf(function1, function2, function3, function4, function5).andThen(function6);
  }

  public static <A, B, C, D, E, F, G, H> Function<A, ? extends H> sequenceOf(
      Function<A, ? extends B> function1,
      Function<? super B, ? extends C> function2,
      Function<? super C, ? extends D> function3,
      Function<? super D, ? extends E> function4,
      Function<? super E, ? extends F> function5,
      Function<? super F, ? extends G> function6,
      Function<? super G, ? extends H> function7) {
    return sequenceOf(function1, function2, function3, function4, function5, function6).andThen(function7);
  }

  public static <A, B, C> Function<A, ? extends C> compositionOf(
      Function<? super B, ? extends C> function1,
      Function<? super A, ? extends B> function2) {
    return function1.compose(function2);
  }

  public static <A, B, C, D> Function<A, ? extends D> compositionOf(
      Function<? super C, ? extends D> function1,
      Function<? super B, ? extends C> function2,
      Function<? super A, ? extends B> function3) {
    return compositionOf(function1, function2).compose(function3);
  }

  public static <A, B, C, D, E> Function<A, ? extends E> compositionOf(
      Function<? super D, ? extends E> function1,
      Function<? super C, ? extends D> function2,
      Function<? super B, ? extends C> function3,
      Function<? super A, ? extends B> function4) {
    return compositionOf(function1, function2, function3).compose(function4);
  }

  public static <A, B, C, D, E, F> Function<A, ? extends F> compositionOf(
      Function<? super E, ? extends F> function1,
      Function<? super D, ? extends E> function2,
      Function<? super C, ? extends D> function3,
      Function<? super B, ? extends C> function4,
      Function<? super A, ? extends B> function5) {
    return compositionOf(function1, function2, function3, function4).compose(function5);
  }

  public static <A, B, C, D, E, F, G> Function<A, ? extends G> compositionOf(
      Function<? super F, ? extends G> function1,
      Function<? super E, ? extends F> function2,
      Function<? super D, ? extends E> function3,
      Function<? super C, ? extends D> function4,
      Function<? super B, ? extends C> function5,
      Function<? super A, ? extends B> function6) {
    return compositionOf(function1, function2, function3, function4, function5).compose(function6);
  }

  public static <A, B, C, D, E, F, G, H> Function<A, ? extends H> compositionOf(
      Function<? super G, ? extends H> function1,
      Function<? super F, ? extends G> function2,
      Function<? super E, ? extends F> function3,
      Function<? super D, ? extends E> function4,
      Function<? super C, ? extends D> function5,
      Function<? super B, ? extends C> function6,
      Function<? super A, ? extends B> function7) {
    return compositionOf(function1, function2, function3, function4, function5, function6).compose(function7);
  }

  public static <A, B> Function<Function<? super A, ? extends B>, B> applyTo(A value) {
    return function -> function.apply(value);
  }
}
