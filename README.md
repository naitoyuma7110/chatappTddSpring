## 各種パッケージのメモ

### spring-test

WebAPI に対する基本的なテスト手段の提供

- WebAPI へのリクエストとレスポンス検証(MockMvcRequestBuilders、AutoConfigureMockMv)
- テストに対する Controller、DB の依存性注入(Autowired)

### DBUnit

DB に関連する基本的なテスト手段の提供

- モック化した DB に対する操作(IDatabaseTester)
- CSV などの定義から検証レコード作成(CsvURLDataSet)
- 比較、検証(Assertion)

### JUnit

テストメソッド実行手順の簡略化

- 複数のテストへの対応、共通処理の関数化、引数渡し(ParameterizedTest,Arguments)

### Spring による DI

Sprint の DI コンテナ(IoC:Inversion of Control)の役割

- 管理対象のインスタンス生成
- 管理対象のインスタンス保持
- 管理対象のインスタンス注入

Spring が管理対象とするインスタンス(Bean)

- @Component
- @Controller
- @RestController
- @Service
- @Repository

## API 一覧

| リクエスト     | HTTP メソッド | エンドポイント | データ形式 | レスポンス                                  |
| -------------- | ------------- | -------------- | ---------- | ------------------------------------------- |
| チャンネル作成 | POST          | /channels      | JSON       | 作成されたチャンネルの詳細                  |
| チャンネル参照 | GET           | /channels      | JSON       | DB に保存されているすべてのチャンネルの詳細 |
