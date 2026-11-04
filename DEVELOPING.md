# 開発・候補検証・公開

利用方法とAPIの意味は[README](README.md)を参照してください。ここではライブラリを変更・公開する人向けの手順を扱います。

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

## 公開物の作成

実装・sources・日本語Javadoc・POM・Gradle Module Metadata（`.module`）と、それぞれの署名・チェックサムを一組で作成します。通常の開発・試験には認証情報は不要です。
公開者は外部で管理する `SIGNING_KEY`（ASCII-armored秘密鍵）と `SIGNING_PASSWORD` を環境へ設定し、次を実行します。値をコマンドラインやリポジトリへ記載しないでください。

```sh
sh gradlew clean check centralBundle -PreleaseBundle
```

出力は `build/central/functional-interface-extension-1.1.3-central.zip` です。`.module`を含む各公開物の署名が欠けるとbundle生成は失敗します。Zip内に各公開物・署名・チェックサムが揃っていることを確認してください。Gradleの導入例は通常のメタデータ参照設定で検証します。
このタスクはCentralへ送信しません。公開者が全ファイル・メタデータ・署名を確認し、[Central Portal](https://central.sonatype.com/publishing)で検証・公開した後、上記 `public` 検証と利用側の更新を行います。
候補検証の成功だけで公開完了とは判断しません。
