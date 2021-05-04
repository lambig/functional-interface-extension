package io.github.lambig.funcifextension.predicate;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ByTest {
  @Nested
  class havingのテスト_一段階版 {
    @Test
    void 変換結果で変換元を評価する述語を返すこと() {
      // SetUp
      // Exercise
      Predicate<Integer> actual = By.<Integer, String>having(String::valueOf).apply(String::isEmpty);
      // Verify
      assertThat(actual.test(1)).isFalse();
    }

    @Test
    void 変換結果で変換元と一致するか評価する述語を返すこと() {
      // SetUp
      // Exercise
      Predicate<Integer> actual = By.<Integer, String>having(String::valueOf).thatEqualsTo("1");
      // Verify
      assertThat(Optional.of(1).filter(actual).isPresent()).isTrue();
    }
  }
}