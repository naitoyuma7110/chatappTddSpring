## 各種パッケージ

### spring-test

WebAPI に対する基本的なテスト手段の提供

- WebAPI へのリクエストとレスポンス検証(MockMvcRequestBuilders、AutoConfigureMockMv)
- テストへの Controller、DB の依存性注入(Autowired)

### DBUnit

DB に関連する基本的なテスト手段の提供

- モック化した DB に対する操作(IDatabaseTester)
- CSV などの定義からレコード作成(CsvURLDataSet)
- 比較、検証(Assertion)

### JUnit

テストメソッド実行手順の簡略化

- 複数のテストへの対応、共通処理の関数化、引数渡し(ParameterizedTest,Arguments)
