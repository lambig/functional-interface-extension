package com.github.lambig.funcifextension.predicate;

import static com.github.lambig.funcifextension.predicate.Predicates.and;
import static com.github.lambig.funcifextension.predicate.Predicates.or;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.function.Predicate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PredicatesTest {
  @Nested
  class andTest {
    @Test
    void conjunction_all_true() {
      //SetUp
      Predicate<Integer> target =
          and(
              x -> x > 0,
              x -> x <= 10);
      //Exercise
      boolean actual = target.test(4);
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void conjunction_contains_false() {
      //SetUp
      Predicate<Integer> target =
          and(
              x -> x > 0,
              x -> x != 4,
              x -> x <= 10);
      //Exercise
      boolean actual = target.test(4);
      //Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class orTest {
    @Test
    void disjunction_all_true() {
      //SetUp
      Predicate<Integer> target =
          or(
              x -> x > 0,
              x -> x <= 10);
      //Exercise
      boolean actual = target.test(4);
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void disjunction_contains_false() {
      //SetUp
      Predicate<Integer> target =
          or(
              x -> x > 0,
              x -> x != 4,
              x -> x <= 10);
      //Exercise
      boolean actual = target.test(4);
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void disjunction_all_false() {
      //SetUp
      Predicate<Integer> target =
          or(
              x -> x <= 0,
              x -> x != 4,
              x -> x > 10);
      //Exercise
      boolean actual = target.test(4);
      //Verify
      assertThat(actual).isFalse();
    }
  }

}