# functional-interface-extension

Javaで、値の変換や条件判定を名前の付いた関数として表現するためのライブラリです。
例えば「入力文字列を数量に変える」「未払いで金額が正の注文を選ぶ」「この注文を受け付けられるかを確かめる」といった処理を、JDKの `Function` / `Predicate` / `Supplier` と組み合わせて記述できます。

- 複数の変換を一つの関数にする: `Functions`
- 三つの値から結果や真偽を求める: `TriFunction` / `TriPredicate`
- 対象の特徴や複数の条件で判定する: `By` / `Equals` / `Predicates`
- 対象が決まった主張を表し、その真偽を尋ねる: `Proposition`（命題）

Java 8以降で動作し、外部runtime依存はありません。
このcheckoutは **1.1.3の公開候補**です。既存公開版は1.1.2です。公開前の候補を試す手順は[開発・候補検証](DEVELOPING.md)を参照してください。

## 導入

公開後はMaven Centralから固定版を取得します。toolboxやABServiceへの依存はありません。

Gradle:

```groovy
repositories { mavenCentral() }
dependencies {
    implementation 'io.github.lambig:functional-interface-extension:1.1.3'
}
```

Maven:

```xml
<dependency>
  <groupId>io.github.lambig</groupId>
  <artifactId>functional-interface-extension</artifactId>
  <version>1.1.3</version>
</dependency>
```

実行時の最小要件はJava 8です。このリポジトリと導入例のbuildにはJDK 17以降を使います。
ソースとJavadocは同じ座標の `sources` / `javadoc` classifierで取得できます。

## Predicate（述語）とProposition（命題）

述語は、対象についての条件です。例えば「xは正の整数である」という述語は、xに何を当てはめるかによって真偽が決まります。
その対象を3と定めた「3は正の整数である」は、真偽を問える**命題**です。「-1は正の整数である」も命題で、その真偽は偽です。

| 表したいもの | Javaでの表現 | 真偽の求め方 |
| --- | --- | --- |
| xは正の整数である（述語 P(x)） | `Predicate<Integer>` | `positiveInteger.test(x)`: 対象xを渡す |
| 3は正の整数である（命題 P(3)） | `Proposition` | `threeIsPositive.test()`: 対象は既に決まっている |
| 命題P(3)の真偽 | `boolean` | `true` |

```java
import io.github.lambig.funcifextension.proposition.Proposition;
import java.util.function.Predicate;

Predicate<Integer> positiveInteger = n -> n > 0; // xは正の整数である
Proposition threeIsPositive = Proposition.subject(3).satisfies(positiveInteger);
Proposition minusOneIsPositive = Proposition.predicate(positiveInteger).satisfiedBy(-1);

boolean three = threeIsPositive.test();                 // true
boolean minusOne = minusOneIsPositive.test();           // false
boolean both = threeIsPositive.and(minusOneIsPositive).test(); // false（かつ）
boolean either = threeIsPositive.or(minusOneIsPositive).test(); // true（または）
```

`subject(3).satisfies(positiveInteger)` と `predicate(positiveInteger).satisfiedBy(3)` は、どちらも「3は正の整数である」という命題を作ります。対象から書くか、条件から書くかを選べます。

Propositionは、**何についての条件かを決める場所と、真偽を尋ねる場所を分けたいとき**に使えます。
例えば「この注文を受け付けられる」という命題を作って渡せば、受け取る側は注文や条件を改めて指定せず、`test()` で可否を確認できます。

真偽は問い合わせた時点で求めます。「この注文は未払いである」のように状態に依存する命題は、支払後には偽になります。
作成時の結果を後で使いたい場合は、その時点の `test()` の戻り値を保存します。

## 値の変換と、対象に適用する条件

```java
import io.github.lambig.funcifextension.function.Functions;
import io.github.lambig.funcifextension.predicate.By;
import io.github.lambig.funcifextension.predicate.Predicates;
import java.util.function.Function;
import java.util.function.Predicate;

Function<String, Integer> quantity = Functions.sequenceOf(String::trim, Integer::parseInt);
int count = quantity.apply(" 3 "); // 3: 前後の空白を除いた文字列を数量にする

Function<Integer, Integer> plusOne = n -> n + 1;
Function<Integer, Integer> twice = n -> n * 2;
int sequential = Functions.sequenceOf(plusOne, twice).apply(3); // 8: (3 + 1) * 2
int composed = Functions.compositionOf(plusOne, twice).apply(3); // 7: (3 * 2) + 1

Predicate<String> threeCharacters = By.having(String::length).thatEqualsTo(3);
Predicate<String> startsWithA = text -> text.startsWith("a");
Predicate<String> matching = Predicates.and(threeCharacters, startsWithA);
boolean accepted = matching.test("abc"); // true: 3文字で、aから始まる
```

単純な二関数の合成にはJDKの `andThen` / `compose` も利用できます。
`Functions.sequenceOf(f, g)` は `g(f(x))`、`compositionOf(f, g)` は `f(g(x))` となる関数を返します。

[実行できる導入例](example/src/main/java/example/OrderExample.java)は、上の述語・命題に加え、注文の金額計算から受付可否までを扱います。
注文が未払いで金額が正の間は受付可能、支払済みになると受付不可、という結果を確認できます。
[MavenとGradleで実行する手順](DEVELOPING.md)を用意しています。

## 目的別のAPI

| API | 得られるもの |
| --- | --- |
| `Functions.sequenceOf(f1, ..., fn)` | f1からfnまでの変換を順につなぎ、最終結果を返す関数（2〜7関数） |
| `Functions.compositionOf(f1, ..., fn)` | fnからf1の順に変換した結果を返す関数（2〜7関数） |
| `Functions.applyTo(value)` | 後から選んだ関数をvalueに適用して、その結果を返す関数 |
| `TriFunction<A,B,C,R>` | 三つの入力から一つの値を求める関数。例: 単価・数量・値引額から合計金額 |
| `TriPredicate<A,B,C>` | 三つの入力が条件を満たすかを判定する述語 |
| `By.having(extractor).that(predicate)` | 対象の特徴が条件を満たすかを判定する述語。例: 名前の長さが3である |
| `By.having(extractor).thatEqualsTo(target)` | 対象の特徴がtargetと等しいかを判定する述語 |
| `Equals.to(target)` | targetと等しいかを判定する述語。配列は要素の内容で比較 |
| `Predicates.and(...)` / `or(...)` | 一つの対象について、全条件／少なくとも一条件を満たすかを判定する述語 |
| `Proposition.subject(value).satisfies(predicate)` | 「valueはpredicateを満たす」という命題 |
| `Proposition.predicate(predicate).satisfiedBy(value)` | 上と同じ命題を、条件から書く形 |
| `Proposition.and(...)` / `or(...)` | 全命題／少なくとも一命題が成り立つという命題 |
| 命題の `and(next)` / `or(next)` | この命題とnextの「かつ」／「または」 |
| `Proposition.test()` / `get()` | 命題の真偽。getはSupplierとして利用できる |
| `Proposition.toPredicate()` | 入力によらず、元の命題が成り立つ場合true、成り立たない場合falseを返す述語 |

`By.That` と二つの `PropositionBuilder...` は `Function` としても使えます。
それぞれの `apply` は `that` / `satisfiedBy` / `satisfies` と同じ結果を返します。

`Predicates.and/or` は、渡された一つの対象に複数の条件を適用する述語です。
`Proposition.and/or` は、それぞれの対象が既に決まった命題を組み合わせます。例えば「この注文は未払いである」と「この商品の在庫がある」を一つの命題にできます。
`toPredicate()` に別の入力を渡しても、命題の対象は変わりません。

## 利用時の約束

- 論理積はすべて真なら真、論理和は一つでも真なら真です。条件は指定順に判定し、結果が決まれば後の条件を判定しません。
- 関数・述語が投げた実行時例外は呼出側へ伝わり、後の処理は実行しません。nullを対象にできるかは、指定する関数・述語の条件によります。
- `Equals.to` では、両値がnullなら真、一方だけがnullなら偽です。両値が配列なら入れ子の配列も含めて内容を比較し、その他の非null値では判定対象の `equals` による等価性を用います。
- 合成する関数・述語・命題には有効な値を指定してください。`Predicates.and/or` と `Proposition.and/or` には一つ以上の条件が必要です。無効な引数の例外と発生時期は各APIのJavadocに記載しています。
- 状態が変わる対象の命題や等価比較では、後の判定結果も変わり得ます。命題の合成に使った配列は作成後に変更しないでください。並行利用できるかは対象と関数の契約によります。

各引数・戻り値・例外は日本語Javadocで確認できます。生成手順は[開発文書](DEVELOPING.md)にあります。

## 更新と切戻し

1.1.2から1.1.3への更新では、利用プロジェクトの版を固定し直してコンパイルと利用側試験を実行します。公開APIと評価結果を変える意図はありません。
問題があれば固定版を1.1.2へ戻し、同じ試験を実行します。データ移行はありません。
独自の関数・述語を組み合わせた処理は、利用側の試験で確認してください。

## ライセンス

[MIT](LICENSE)。
