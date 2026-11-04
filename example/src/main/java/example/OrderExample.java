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

/** 述語と命題の違い、および注文の計算・判定を示す導入例。 */
public final class OrderExample {
  private OrderExample() {}

  public static void main(String[] args) {
    // 述語: 「xは正の整数である」。対象ごとに同じ条件を使える。
    Predicate<Integer> positiveInteger = n -> n > 0;
    // 命題: 「3は正の整数である」「-1は正の整数である」。対象が決まっている。
    Proposition threeIsPositive = Proposition.subject(3).satisfies(positiveInteger);
    Proposition minusOneIsPositive = Proposition.predicate(positiveInteger).satisfiedBy(-1);
    require(threeIsPositive.test(), "3は正の整数である");
    require(!minusOneIsPositive.test(), "-1は正の整数ではない");
    require(!threeIsPositive.and(minusOneIsPositive).test(), "二つの命題の論理積は偽");
    require(threeIsPositive.or(minusOneIsPositive).test(), "二つの命題の論理和は真");

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

    // 「注文xを受け付けられる」という述語に、この注文を結び付ける。
    // 命題readyは「この注文を受け付けられる」を表し、対象を渡さず真偽を尋ねられる。
    Proposition ready = Proposition.subject(order).satisfies(acceptable);
    require(ready.test(), "この注文は未払いで金額が正なので受け付けられる");
    order.status = "paid";
    require(!ready.get(), "支払済みになったこの注文は受け付けられない");
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
