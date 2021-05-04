package io.github.lambig.funcifextension.function;

import static io.github.lambig.funcifextension.function.Functions.compositionOf;
import static io.github.lambig.funcifextension.function.Functions.sequenceOf;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FunctionsTest {
  @Nested
  class sequenceOfTest {
    @Test
    void done_sequentially() {
      // SetUp
      Function<String, Integer> parseInt = Integer::parseInt;
      Function<Integer, String> toString = String::valueOf;
      Function<? super Integer, ? extends String> target =
          Functions.sequenceOf(
              toString,
              parseInt,
              i -> i * 2,
              i -> i + 1,
              toString,
              parseInt,
              toString);
      //Exercise
      String actual = Optional.of(11).map(target).get();
      //Verify
      assertThat(actual).isEqualTo("23");
    }
  }

  @Nested
  class compositionOfTest {
    @Test
    void done_reverse_sequentially() {
      // SetUp
      Function<String, Integer> parseInt = Integer::parseInt;
      Function<Integer, String> toString = String::valueOf;
      Function<? super Integer, ? extends String> target =
          Functions.compositionOf(
              toString,
              parseInt,
              toString,
              i -> i * 2,
              i -> i + 1,
              parseInt,
              toString);
      //Exercise
      String actual = Optional.of(11).map(target).get();
      //Verify
      assertThat(actual).isEqualTo("24");
    }
  }

  @Nested
  class applyToTest {
    @Test
    void done_reverse_sequentially() {
      // SetUp
      Stream<UnaryOperator<Integer>> functions =
          Stream
              .of(
                  x -> x + 1,
                  x -> x + 2,
                  x -> x + 3,
                  x -> x + 4,
                  x -> x + 5,
                  x -> x + 6);
      //Exercise
      List<Integer> actual = functions.map(Functions.applyTo(1)).collect(toList());
      //Verify
      assertThat(actual).containsExactly(2, 3, 4, 5, 6, 7);
    }
  }
}