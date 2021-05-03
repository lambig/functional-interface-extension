package com.github.lambig.funcifextension.proposition;

import static com.github.lambig.funcifextension.proposition.Proposition.and;
import static com.github.lambig.funcifextension.proposition.Proposition.or;
import static com.github.lambig.funcifextension.proposition.Proposition.predicate;
import static com.github.lambig.funcifextension.proposition.Proposition.subject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;


import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PropositionTest {
  @Nested
  class i_f_test {
    @Test
    void evaluation() {
      // SetUp
      Proposition target = () -> true;
      // Exercise
      boolean actual = target.get();
      // Verify
      assertThat(actual).isTrue();
    }
  }

  @Nested
  class and_static_test {
    @Test
    void conjunction_all_true() {
      // SetUp
      Proposition target =
          and(
              () -> true,
              () -> 1 == 2 - 1);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void conjunction_contains_false() {
      // SetUp
      Proposition target =
          and(
              () -> true,
              () -> 1 > 2);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isFalse();
    }

    @Test
    void no_proposition() {
      // SetUp
      // Exercise
      Throwable exception = catchThrowable(() -> and());
      // Verify
      assertThat(exception)
          .extracting(IllegalArgumentException.class::cast)
          .extracting(Exception::getMessage)
          .isEqualTo("At least 1 proposition needed");
    }
  }

  @Nested
  class or_static_test {
    @Test
    void disjunction_all_true() {
      // SetUp
      Proposition target =
          or(
              () -> true,
              () -> 1 == 2 - 1);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void disjunction_contains_false() {
      // SetUp
      Proposition target =
          or(
              () -> true,
              () -> 1 > 2);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void disjunction_all_false() {
      // SetUp
      Proposition target =
          or(
              () -> false,
              () -> 1 > 2);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isFalse();
    }


    @Test
    void no_proposition() {
      // SetUp
      // Exercise
      Throwable exception = catchThrowable(() -> or());
      // Verify
      assertThat(exception)
          .extracting(IllegalArgumentException.class::cast)
          .extracting(Exception::getMessage)
          .isEqualTo("At least 1 proposition needed");
    }
  }

  @Nested
  class predicate_subject_Test {
    @Test
    void satisfied() {
      // SetUp
      Predicate<Integer> is2 = x -> x == 2;
      Proposition target = predicate(is2).satisfiedBy(1 + 1);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void not_satisfied() {
      // SetUp
      Predicate<Integer> is2 = x -> x == 3;
      Proposition target = predicate(is2).satisfiedBy(1 + 1);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class subject_predicate_Test {
    @Test
    void satisfied() {
      // SetUp
      Proposition target = subject(1 + 1).satisfies(x -> x == 2);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void not_satisfied() {
      // SetUp
      Proposition target = subject(1 + 1).satisfies(x -> x == 3);
      // Exercise
      boolean actual = target.test();
      // Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class toPredicate_Test {
    @Test
    void satisfied() {
      // SetUp
      Proposition target = subject(1 + 1).satisfies(x -> x == 2);
      // Exercise
      boolean actual =
          Stream
              .<Predicate<Integer>>of(
                  x -> x > 1,
                  x -> x < 10,
                  target.toPredicate())
              .allMatch(p -> p.test(3));
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void not_satisfied() {
      // SetUp
      Proposition target = subject(1 + 1).satisfies(x -> x == 3);

      // Exercise
      boolean actual =
          Stream
              .<Predicate<Integer>>of(
                  x -> x > 1,
                  x -> x < 10,
                  target.toPredicate())
              .allMatch(p -> p.test(3));
      // Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class and_instance_test {
    @Test
    void conjunction_all_true() {
      // SetUp
      Proposition target = () -> true;
      // Exercise
      boolean actual = target.and(() -> 1 == 2 - 1).test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void conjunction_contains_false() {
      // SetUp
      Proposition target = () -> true;
      // Exercise
      boolean actual = target.and(() -> 2 < 1).test();
      // Verify
      assertThat(actual).isFalse();
    }

  }

  @Nested
  class or_instance_test {
    @Test
    void disjunction_all_true() {
      // SetUp
      Proposition target = () -> true;
      // Exercise
      boolean actual = target.or(() -> 1 == 2 - 1).test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void disjunction_contains_false() {
      // SetUp
      Proposition target = () -> true;
      // Exercise
      boolean actual = target.or(() -> 2 < 1).test();
      // Verify
      assertThat(actual).isTrue();
    }

    @Test
    void disjunction_all_false() {
      // SetUp
      Proposition target = () -> false;
      // Exercise
      boolean actual = target.or(() -> 2 < 1).test();
      // Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class generate_test {
    @Test
    void generates_from_predicate() {
      // SetUp
      Predicate<Integer> lessThan10 = x -> x < 10;
      // Exercise
      Proposition target = and(IntStream.range(0, 5).boxed().map(predicate(lessThan10)).toArray(Proposition[]::new));
      // Verify
      assertThat(target.test()).isTrue();
    }

    @Test
    void generates_from_subject() {
      // SetUp
      Predicate<Integer> lessThan10 = x -> x < 10;
      // Exercise
      Proposition target = and(IntStream.range(0, 5).boxed().map(Proposition::subject).map(f -> f.apply(lessThan10)).toArray(Proposition[]::new));
      // Verify
      assertThat(target.test()).isTrue();
    }
  }
}