package io.github.lambig.funcifextension.predicate;

import java.util.Objects;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Equals {
  public static <O> Predicate<O> to(O target) {
    return evaluated -> Objects.deepEquals(evaluated, target);
  }
}
