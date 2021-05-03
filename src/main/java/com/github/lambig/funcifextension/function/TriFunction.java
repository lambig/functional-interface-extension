package com.github.lambig.funcifextension.function;

public interface TriFunction<A, B, C, X> {
  X apply(A a, B b, C c);
}
