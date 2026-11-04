package io.github.lambig.funcifextension;

import static org.junit.jupiter.api.Assertions.*;

import io.github.lambig.funcifextension.function.Functions;
import io.github.lambig.funcifextension.predicate.By;
import io.github.lambig.funcifextension.predicate.Equals;
import io.github.lambig.funcifextension.predicate.Predicates;
import io.github.lambig.funcifextension.proposition.Proposition;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

/** 文書化した評価時期・短絡評価・参照保持の契約。 */
class PublicContractTest {
  @Test
  void compositionIsDeferredOrderedAndStopsAtTheOriginalException() {
    List<String> calls = new ArrayList<>();
    Function<String, String> first = s -> { calls.add("first"); return s + "1"; };
    Function<String, String> second = s -> { calls.add("second"); return s + "2"; };
    Function<String, String> sequence = Functions.sequenceOf(first, second);
    assertTrue(calls.isEmpty());
    assertEquals("12", sequence.apply(""));
    assertEquals(java.util.Arrays.asList("first", "second"), calls);
    calls.clear();
    assertEquals("21", Functions.compositionOf(first, second).apply(""));
    assertEquals(java.util.Arrays.asList("second", "first"), calls);
    calls.clear();
    IllegalStateException failure = new IllegalStateException("fixture");
    Function<String, String> fail = s -> { throw failure; };
    assertSame(failure, assertThrows(IllegalStateException.class,
        () -> Functions.sequenceOf(first, fail, second).apply("")));
    assertEquals(java.util.Arrays.asList("first"), calls);
  }

  @Test
  void functionNullChecksHappenAtTheDocumentedTime() {
    Function<String, String> identity = Function.identity();
    assertThrows(NullPointerException.class, () -> Functions.sequenceOf(identity, null));
    assertThrows(NullPointerException.class, () -> Functions.compositionOf(null, identity));
    Function<Function<? super String, ? extends String>, String> captured = Functions.applyTo(null);
    assertNull(captured.apply(identity));
    assertThrows(NullPointerException.class, () -> captured.apply(null));
    assertNull(Functions.sequenceOf(identity, identity).apply(null));
  }

  @Test
  void predicatesRejectInvalidConstructionAndShortCircuitEvaluation() {
    AtomicInteger calls = new AtomicInteger();
    Predicate<Object> counted = v -> { calls.incrementAndGet(); return true; };
    assertThrows(IllegalArgumentException.class, () -> Predicates.and());
    assertThrows(IllegalArgumentException.class, () -> Predicates.or());
    assertThrows(NullPointerException.class, () -> Predicates.and((Predicate<Object>[]) null));
    assertThrows(NullPointerException.class, () -> Predicates.or(counted, null));
    assertThrows(NullPointerException.class, () -> Predicates.and((Predicate<Object>) null));
    assertFalse(Predicates.and(v -> false, counted).test(null));
    assertTrue(Predicates.or(v -> true, counted).test(null));
    assertEquals(0, calls.get());
    IllegalStateException failure = new IllegalStateException("fixture");
    Predicate<Object> fail = v -> { throw failure; };
    assertSame(failure, assertThrows(IllegalStateException.class,
        () -> Predicates.and(fail, counted).test(null)));
    assertSame(failure, assertThrows(IllegalStateException.class,
        () -> Predicates.or(fail, counted).test(null)));
    assertEquals(0, calls.get());
  }

  @Test
  void propertyPredicatesDeferNullChecksAndDeepEqualityIncludesArrays() {
    Predicate<String> nullExtractor = By.<String, String>having(null).that(v -> true);
    Predicate<String> nullPredicate = By.having((String s) -> s).that(null);
    assertThrows(NullPointerException.class, () -> nullExtractor.test("x"));
    assertThrows(NullPointerException.class, () -> nullPredicate.test("x"));
    assertTrue(By.having((String s) -> s).thatEqualsTo(null).test(null));
    assertTrue(Equals.to(new Object[] {new int[] {1, 2}, null})
        .test(new Object[] {new int[] {1, 2}, null}));
    int[] target = {1};
    Predicate<int[]> matches = Equals.to(target);
    target[0] = 2;
    assertTrue(matches.test(new int[] {2}));
  }

  @Test
  void propositionsReevaluateCapturedReferencesAndNullElementsAreLazy() {
    AtomicInteger state = new AtomicInteger();
    Proposition current = Proposition.subject(state).satisfies(n -> n.get() > 0);
    assertFalse(current.test());
    state.incrementAndGet();
    assertTrue(current.get());
    assertTrue(current.toPredicate().test(null));
    assertTrue(Proposition.predicate((Object o) -> o == null).apply(null).test());
    assertTrue(Proposition.subject(state).apply(n -> n.get() == 1).test());
    AtomicInteger gate = new AtomicInteger();
    Proposition[] terms = {() -> gate.get() > 0, null};
    Proposition all = Proposition.and(terms);
    assertFalse(all.test());
    gate.incrementAndGet();
    assertThrows(NullPointerException.class, all::test);
    assertTrue(Proposition.or(new Proposition[] {() -> true, null}).test());
    Proposition invalid = Proposition.<Object>predicate(null).satisfiedBy(null);
    assertThrows(NullPointerException.class, invalid::test);
    assertThrows(IllegalArgumentException.class, () -> Proposition.and(new Proposition[0]));
    assertThrows(IllegalArgumentException.class, () -> Proposition.or(new Proposition[0]));
    assertThrows(NullPointerException.class, () -> Proposition.and((Proposition[]) null));
  }

  @Test
  void conjunctionKeepsItsOperandsWhenTheCallerReusesTheArray() {
    Proposition[] terms = {() -> true, () -> true};
    Proposition all = Proposition.and(terms);
    terms[0] = null;
    terms[1] = () -> false;
    assertTrue(all.test());
    java.util.Arrays.fill(terms, null);
    assertTrue(all.get());
  }

  @Test
  void disjunctionKeepsItsOperandsWhenTheCallerReusesTheArray() {
    Proposition[] terms = {() -> false, () -> false};
    Proposition any = Proposition.or(terms);
    terms[0] = () -> true;
    terms[1] = null;
    assertFalse(any.test());
    java.util.Arrays.fill(terms, null);
    assertFalse(any.get());
  }

  @Test
  void compositionKeepsThePropositionsLiveAndShortCircuitsInTheOriginalOrder() {
    AtomicInteger state = new AtomicInteger();
    AtomicInteger laterCalls = new AtomicInteger();
    Proposition positive = Proposition.subject(state).satisfies(n -> n.get() > 0);
    Proposition later = () -> { laterCalls.incrementAndGet(); return true; };
    Proposition all = Proposition.and(positive, later);
    Proposition any = Proposition.or(positive, later);
    assertFalse(all.test());
    assertEquals(0, laterCalls.get());
    state.set(1);
    assertTrue(all.test());
    assertEquals(1, laterCalls.get());
    assertTrue(any.test());
    assertEquals(1, laterCalls.get());
    state.set(0);
    assertTrue(any.test());
    assertEquals(2, laterCalls.get());
  }

  @Test
  void copyingTheOperandArrayPreservesLazyNullFailuresAndOriginalExceptions() {
    Proposition[] terms = {() -> false, null};
    Proposition any = Proposition.or(terms);
    terms[1] = () -> true;
    assertThrows(NullPointerException.class, any::test);
    assertTrue(Proposition.or(new Proposition[] {() -> true, null}).test());
    assertFalse(Proposition.and(new Proposition[] {() -> false, null}).test());
    IllegalStateException failure = new IllegalStateException("fixture");
    Proposition fail = () -> { throw failure; };
    assertSame(failure, assertThrows(IllegalStateException.class,
        () -> Proposition.and(() -> true, fail).test()));
    assertSame(failure, assertThrows(IllegalStateException.class,
        () -> Proposition.or(() -> false, fail).test()));
    assertThrows(NullPointerException.class, () -> Proposition.or((Proposition[]) null));
  }

  @Test
  void predicateCompositionAlreadyKeepsItsSelectedConditions() {
    @SuppressWarnings("unchecked")
    Predicate<Integer>[] conditions = (Predicate<Integer>[]) new Predicate<?>[2];
    conditions[0] = n -> n > 0;
    conditions[1] = n -> n % 2 == 0;
    Predicate<Integer> all = Predicates.and(conditions);
    Predicate<Integer> any = Predicates.or(conditions);
    conditions[0] = null;
    conditions[1] = n -> false;
    assertTrue(all.test(2));
    assertFalse(all.test(1));
    assertTrue(any.test(1));
    assertFalse(any.test(-1));
  }

  @Test
  void manyPredicatesCanBeCombinedWithoutADeepCallStack() {
    @SuppressWarnings("unchecked")
    Predicate<Object>[] conditions = (Predicate<Object>[]) new Predicate<?>[10000];
    AtomicInteger calls = new AtomicInteger();
    Predicate<Object> accept = value -> { calls.incrementAndGet(); return true; };
    java.util.Arrays.fill(conditions, accept);
    assertTrue(Predicates.and(conditions).test(null));
    assertEquals(10000, calls.get());
    calls.set(0);
    Predicate<Object> reject = value -> { calls.incrementAndGet(); return false; };
    java.util.Arrays.fill(conditions, reject);
    assertFalse(Predicates.or(conditions).test(null));
    assertEquals(10000, calls.get());
  }
}
