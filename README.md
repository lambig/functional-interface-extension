# functional-interface-extension

Javaの関数合成、三引数の関数・述語、対象を捕捉する遅延判定を補うライブラリです。
JDKの `Function` / `Predicate` / `Supplier` と組み合わせて使います。
Java 8以降で動作し、外部runtime依存はありません。単純な二関数の合成にはJDKの `andThen` / `compose` も利用できます。

このcheckoutは **1.1.3の公開候補**です。Maven Central公開前の確認には下記の `candidate` 手順を使ってください。
既存公開版は1.1.2です。1.1.3では公開APIと評価動作を維持し、build・公開物・文書・導入例を整えています。

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

## 最初の例

```java
import io.github.lambig.funcifextension.function.Functions;
import io.github.lambig.funcifextension.predicate.By;
import io.github.lambig.funcifextension.proposition.Proposition;
import java.util.function.Function;
import java.util.function.Predicate;

Function<String, Integer> parse = Functions.sequenceOf(String::trim, Integer::parseInt);
int quantity = parse.apply(" 3 "); // 3
Predicate<String> threeCharacters = By.having(String::length).thatEqualsTo(3);
boolean matches = threeCharacters.test("abc"); // true
Proposition ready = Proposition.subject("abc").satisfies(threeCharacters);
boolean result = ready.test(); // true。呼出しのたびに評価する
```

[実行できる注文例](example/src/main/java/example/OrderExample.java)は、三引数での金額計算、プロパティ抽出、複合条件、注文変更後の再評価まで含みます。
ライブラリのソースを同時にコンパイルせず、[Gradle](example/build.gradle) と [Maven](example/pom.xml) が同じPOM/JARを取得します。
成功時の出力:

```text
subtotal=1000; pending-before=true; pending-after=false; OK
```

## APIと評価の契約

| API | 動作 |
| --- | --- |
| `Functions.sequenceOf(f1, ..., fn)` | 2〜7関数を左から右へ合成。`apply` 時に実行 |
| `Functions.compositionOf(f1, ..., fn)` | 2〜7関数を右から左へ合成。数学の関数合成と同じ順序 |
| `Functions.applyTo(value)` | 値を先に捕捉し、後から渡された関数に適用 |
| `TriFunction<A,B,C,R>` / `TriPredicate<A,B,C>` | 三引数の `apply` / `test` を表す関数型インターフェース |
| `By.having(extractor).that(predicate)` | 値を抽出して判定。`thatEqualsTo(target)` は抽出値を深く比較 |
| `Equals.to(target)` | `Objects.deepEquals` による比較。null、基本型配列、入れ子の配列も扱う |
| `Predicates.and(...)` / `or(...)` | 引数順の論理積・論理和。最初のfalse／trueで短絡 |
| `Proposition.subject(value).satisfies(predicate)` | 対象を先に指定する遅延命題 |
| `Proposition.predicate(predicate).satisfiedBy(value)` | 述語を先に指定する遅延命題 |
| `Proposition.test()` / `get()` | 呼出しごとに再評価。結果をキャッシュしない |
| `Proposition.and(...)` / `or(...)`、インスタンスの `and(next)` / `or(next)` | 命題を引数順に短絡評価 |
| `Proposition.toPredicate()` | 渡された入力を使用せず、その都度命題を評価 |

`By.That` と二つの `PropositionBuilder...` は `Function` でもあり、`apply` で対応する `that` / `satisfiedBy` / `satisfies` を呼べます。

### null・例外・可変値

- `Functions` の合成ではnullの関数を合成時に拒否します。入力値や中間値のnullを扱えるかは渡した関数次第です。`applyTo(null)` はnullを保持し、nullの関数を渡すと評価時に例外になります。
- `Predicates.and/or` は空配列で `IllegalArgumentException`、nullの配列・要素で合成時に `NullPointerException` となります。入力値は各述語へそのまま渡します。
- `By.having` のnull抽出関数・`that` のnull述語は、完成した述語の評価時に例外になります。抽出結果のnullも判定する述語へそのまま渡します。
- `Proposition.and/or` は空配列を拒否し、null配列も組立て時に拒否します。null要素は評価が到達したときだけ例外になります。述語指定がnullの場合も、命題の評価時に例外になります。
- 合成中に利用者の関数・述語が投げた実行時例外はそのまま伝わり、後続は実行しません。
- `Proposition` の対象、合成に渡した命題配列、`Equals.to` の比較対象は参照を保持します。コピーや同期は行いません。対象の変更は後の評価へ反映されます。命題配列は組立て後に変更しないでください。並行利用時の安全性は捕捉した対象と関数次第です。

各引数・戻り値・例外の詳細は `sh gradlew javadoc` で生成する `build/docs/javadoc/index.html` にあります。API文書は日本語です。

## buildと導入例の検証

JDK 17以降、Maven 3.9以降、Python 3、POSIX shellを使用します。WindowsではWSL等のPOSIX環境で次の共通スクリプトを実行できます。
GradleはこのリポジトリのWrapper（9.7.1）で固定しています。

```sh
# ライブラリの試験と候補POM/JAR・sources・Javadocの生成
sh gradlew clean check publishMavenJavaPublicationToCandidateRepository

# 空の依存キャッシュで、独立したMaven／Gradleの利用と既存API互換性を検証
sh verify-consumers.sh candidate
```

`TEST_JAVA_HOME` をJava 8など別のJDKへ設定すると、build用JDKを維持したまま試験と導入例の実行JDKを変更できます。
CIはbuildにJDK 17、実行にJava 8／25を使います。Java 8 bytecode、POMの外部依存ゼロ、文書JARの内容、1.1.2との公開シグネチャ一致、1.1.2でコンパイルした利用コードの実行を検査します。
結果は `build/consumer-results/candidate/` と `build/compatibility/` に出力します。

候補リポジトリは `build/repository` です。`mavenLocal` やcomposite buildでソースを置換しません。個別に動かす場合:

```sh
sh gradlew -p example -PcandidateRepository="$PWD/build/repository" clean runExample
mvn -f example/pom.xml -DcandidateRepository="file://$PWD/build/repository" clean compile dependency:build-classpath -Dmdep.outputFile=target/runtime-classpath.txt
java -cp "example/target/classes:$(cat example/target/runtime-classpath.txt)" example.OrderExample
```

公開完了後は同じ導入例を候補リポジトリなしで確認します。

```sh
sh verify-consumers.sh public 1.1.3
```

## 1.1.2からの更新と切戻し

1. 利用プロジェクトでバージョンを1.1.3へ固定し、依存解決・コンパイル・利用側試験を実行します。
2. `sequenceOf` / `compositionOf` の順序や、可変対象を使う命題が想定どおりか確認します。更新前後で公開シグネチャと動作を変える意図はありません。
3. 問題がある場合は依存の固定版を1.1.2へ戻し、同じ試験を実行します。データ移行はありません。

このライブラリの互換性検査は全利用プロジェクトの動作を保証するものではありません。利用側で独自の関数・述語を組み合わせた経路は、利用側の試験で確認してください。

## 公開物の作成

実装・sources・日本語Javadoc・POMと署名を一組で作成します。通常の開発・試験には認証情報は不要です。
公開者は外部で管理する `SIGNING_KEY`（ASCII-armored秘密鍵）と `SIGNING_PASSWORD` を環境へ設定し、次を実行します。値をコマンドラインやリポジトリへ記載しないでください。

```sh
sh gradlew clean check centralBundle -PreleaseBundle
```

出力は `build/central/functional-interface-extension-1.1.3-central.zip` です。署名が欠けるとbundle生成は失敗します。
このタスクはCentralへ送信しません。公開者が全ファイル・メタデータ・署名を確認し、[Central Portal](https://central.sonatype.com/publishing)で検証・公開した後、上記 `public` 検証と利用側の更新を行います。
候補検証の成功だけで公開完了とは判断しません。

## ライセンス

[MIT](LICENSE)。実装JAR・sources・Javadocには `META-INF/LICENSE` を含めます。
