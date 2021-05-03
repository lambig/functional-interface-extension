package com.github.lambig.funcifextension.predicate;

import java.util.Arrays;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Predicates {
  @SafeVarargs
  public static <E> Predicate<E> and(Predicate<E>... predicate) {
    return
        Arrays
            .stream(predicate)
            .reduce(Predicate::and)
            .orElseThrow(() -> new IllegalArgumentException("At least 1 predicate needed"));
  }

  @SafeVarargs
  public static <E> Predicate<E> or(Predicate<E>... predicate) {
    return
        Arrays
            .stream(predicate)
            .reduce(Predicate::or)
            .orElseThrow(() -> new IllegalArgumentException("At least 1 predicate needed"));
  }
}
