# 開発メモ

## ディレクトリ構成

src  
└ main  
 └ java  
 ├ app  
 │ ├ controller ※Controller を配置  
 │ └ service ※Application Service を配置  
 ├ domain  
 │ └ [ドメイン名]  
 │ ├ model ※Domain Model を配置  
 │ └ service ※Domain Service と Repository インターフェースを配置  
 └ infrastructure  
 └ [ドメイン名] ※Repository 実装クラスを配置

## spring-test

WebAPI に対する基本的なテスト手段の提供

- WebAPI へのリクエストとレスポンス検証(MockMvcRequestBuilders、AutoConfigureMockMv)
- テストに対する Controller、DB の依存性注入(Autowired)

## テストの概要

API、DB をモック化しテストに必要な条件を揃えて各種リクエストを実行  
レスポンスと実行後の DB 内容を検証

![image](https://github.com/naitoyuma7110/chatappTddSpring/assets/128150297/4669c9e1-dbb6-449a-bc3f-8724caede421)


## JUnit

テストメソッド実行手順の簡略化

- 複数のテストへの対応、共通処理の関数化、引数渡し(ParameterizedTest,Arguments)

## DBUnit

DB に関連する基本的なテスト手段の提供

- モック化した DB に対する操作(IDatabaseTester)
- CSV などの定義から検証レコード作成(CsvURLDataSet)
- 比較、検証(Assertion)

## Spring による DI

Sprint の DI コンテナ(IoC:Inversion of Control)の役割

- 管理対象のインスタンス生成
- 管理対象のインスタンス保持
- 管理対象のインスタンス注入

Spring が管理対象とするインスタンス(Bean)

@ComponentScan の対象

- @Component
- @Controller
- @RestController
- @Service
- @Repository

## MyBatis

### H2 DB の利用

application.yml に仮想 DB として H2 の接続設定を記載
main と test 下の"./resource/schema.sql"が起動の度に実行される

### MyBatis を対象とした ORM の依存性管理

MyBatis の@Mapper は@ComponentScan では DI 対象に追加されない。
@Mapper を DI 対象に追加するためには、MyBatis が提供する@MapperScan を Configuration クラスに追加する。

しかし、今回はセットアップ時に mybatis-starter を選択に含めたため@MapperScan の省略が可能

## Controller に対するリクエストバリデーション

パッケージ

- spring-boot-starter-validation
- validation-api

[https://www.baeldung.com/spring-boot-bean-validation](https://sebenkyo.com/2020/08/02/post-1260/)

## Controller の共通の例外ハンドラ

@RestControllerAdvice の利用

https://zenn.dev/karaageeeee/articles/cb428b126e82ea

## フォーマッター設定方法

VSCode の Java 開発環境にフォーマッターを設定する  
https://qiita.com/ryo8000/items/60714fa9c5ce261c1798
