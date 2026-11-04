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
    Proposition[] terms = {() -> false, null};
    Proposition all = Proposition.and(terms);
    assertFalse(all.test());
    terms[0] = () -> true;
    assertThrows(NullPointerException.class, all::test);
    assertTrue(Proposition.or(new Proposition[] {() -> true, null}).test());
    Proposition invalid = Proposition.<Object>predicate(null).satisfiedBy(null);
    assertThrows(NullPointerException.class, invalid::test);
    assertThrows(IllegalArgumentException.class, () -> Proposition.and(new Proposition[0]));
    assertThrows(IllegalArgumentException.class, () -> Proposition.or(new Proposition[0]));
    assertThrows(NullPointerException.class, () -> Proposition.and((Proposition[]) null));
  }
}
