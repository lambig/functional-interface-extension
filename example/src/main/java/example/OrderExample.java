package example;

import io.github.lambig.funcifextension.function.Functions;
import io.github.lambig.funcifextension.function.TriFunction;
import io.github.lambig.funcifextension.predicate.By;
import io.github.lambig.funcifextension.predicate.Equals;
import io.github.lambig.funcifextension.predicate.Predicates;
import io.github.lambig.funcifextension.predicate.TriPredicate;
import io.github.lambig.funcifextension.proposition.Proposition;
import java.util.function.Function;
import java.util.function.Predicate;

/** 外部JARの公開APIだけで注文の変換・判定を行う導入例。 */
public final class OrderExample {
  private OrderExample() {}

  public static void main(String[] args) {
    Function<String, Integer> quantity = Functions.sequenceOf(String::trim, Integer::parseInt);
    TriFunction<Integer, Integer, Integer, Integer> subtotal = (price, count, discount) ->
        price * count - discount;
    TriPredicate<Integer, Integer, Integer> payable = (price, count, discount) ->
        price > 0 && count > 0 && discount >= 0 && discount <= price * count;
    int count = quantity.apply(" 3 ");
    require(payable.test(400, count, 200), "支払条件");
    int amount = subtotal.apply(400, count, 200);
    require(amount == 1000, "合計金額");
    require(Functions.<Integer, String>applyTo(amount).apply(Object::toString).equals("1000"),
        "値を先に指定する変換");
    Function<String, Integer> composed = Functions.compositionOf(Integer::parseInt, String::trim);
    require(composed.apply(" 3 ") == count, "右から左への合成");

    Order order = new Order("pending", amount);
    Predicate<Order> pending = By.having((Order o) -> o.status).thatEqualsTo("pending");
    Predicate<Order> positive = By.having((Order o) -> o.amount).that(n -> n > 0);
    Predicate<Order> acceptable = Predicates.and(pending, positive);
    require(acceptable.test(order), "注文判定");
    require(Predicates.or(pending, o -> false).test(order), "選択条件");
    require(Equals.to(new int[] {400, 3, 200}).test(new int[] {400, 3, 200}), "配列の等価性");

    Proposition ready = Proposition.subject(order).satisfies(acceptable);
    require(ready.test(), "変更前の評価");
    order.status = "paid";
    require(!ready.get(), "捕捉した注文の変更を再評価");
    require(!ready.toPredicate().test(null), "入力を使用しない述語への変換");
    require(Proposition.predicate(positive).satisfiedBy(order).test(), "述語を先に指定");
    require(ready.or(() -> true).and(() -> true).test(), "命題の短絡合成");
    System.out.println("subtotal=1000; pending-before=true; pending-after=false; OK");
  }

  private static void require(boolean result, String description) {
    if (!result) throw new AssertionError(description);
  }

  private static final class Order {
    private String status;
    private final int amount;

    private Order(String status, int amount) {
      this.status = status;
      this.amount = amount;
    }
  }
}
