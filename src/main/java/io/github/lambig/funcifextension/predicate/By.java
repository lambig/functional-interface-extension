package io.github.lambig.funcifextension.predicate;

import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class By {

  public static <O, F> That<O, F> having(Function<O, F> extractor) {
    return fPredicate -> o -> fPredicate.test(extractor.apply(o));
  }

  public interface That<A, B> extends Function<Predicate<B>, Predicate<A>> {

    @Override
    default Predicate<A> apply(Predicate<B> original) {
      return this.that(original);
    }

    Predicate<A> that(Predicate<B> bPredicate);

    default Predicate<A> thatEqualsTo(B target) {
      return this.that(Equals.to(target));
    }

  }
}


