package io.github.lambig.funcifextension.proposition;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Proposition extends Supplier<Boolean> {
  boolean test();

  static Proposition and(Proposition... propositions) {
    if (propositions.length == 0) {
      throw new IllegalArgumentException("At least 1 proposition needed");
    }
    return () -> Arrays.stream(propositions).allMatch(Proposition::test);
  }

  static Proposition or(Proposition... propositions) {
    if (propositions.length == 0) {
      throw new IllegalArgumentException("At least 1 proposition needed");
    }
    return () -> Arrays.stream(propositions).anyMatch(Proposition::test);
  }

  static <E> PropositionBuilderFromPredicate<E> predicate(Predicate<E> predicate) {
    return subject -> () -> predicate.test(subject);
  }

  static <E> PropositionBuilderFromSubject<E> subject(E subject) {
    return predicate -> () -> predicate.test(subject);
  }

  @Override
  default Boolean get() {
    return this.test();
  }

  default <E> Predicate<E> toPredicate() {
    return anything -> this.test();
  }

  default Proposition and(Proposition next) {
    return and(this, next);
  }

  default Proposition or(Proposition next) {
    return or(this, next);
  }

  interface PropositionBuilderFromPredicate<S> extends Function<S, Proposition> {
    Proposition satisfiedBy(S subject);

    @Override
    default Proposition apply(S s) {
      return this.satisfiedBy(s);
    }
  }

  interface PropositionBuilderFromSubject<S> extends Function<Predicate<S>, Proposition> {
    Proposition satisfies(Predicate<S> predicate);

    @Override
    default Proposition apply(Predicate<S> s) {
      return this.satisfies(s);
    }
  }
}
