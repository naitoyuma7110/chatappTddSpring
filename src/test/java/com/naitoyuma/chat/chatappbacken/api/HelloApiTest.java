package com.naitoyuma.chat.chatappbacken.api;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
import java.net.URL;
import javax.sql.DataSource;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloApiTest {
  // 各種コントローラーを含むMVC環境をMockMvcとしてモック化しテスト用のメソッドで簡易的に使用可能にする
  @Autowired
  private MockMvc mockMvc;

  // 各種DBテーブルを含むDBをDataSourceとしてモック化しテスト用のメソッドで簡易的に使用可能にする
  @Autowired
  private DataSource dataSource;

  // ParameterizedTest：引き数としてテスト設定を受け取るためインスタンス化して実行する
  @ParameterizedTest
  @MethodSource("helloTestProvider") // 静的Providerメソッドを使用してパラメータを渡す
  public void helloTest(String queryString, String expectedBody, String dbPath) throws Exception {

    // Given:テスト実行に必要なレコードをDBのモックにセットする
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/hello/hello/" + dbPath + "/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // Controllerのモックを使用しリクエストとレスポンスの検証を行う(modelやserviceなど他レイヤーとは切り離されている)
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/hello" + queryString).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));

    // DBのモックを使用しDBデータの整合性の検証を行う(Controllerやserviceなど他レイヤーとは切り離されている)
    var actualDataSet = databaseTester.getConnection().createDataSet();
    // テスト実行後のDBレコード
    var actualTestTable = actualDataSet.getTable("test");

    URL expectedUrl = this.getClass().getResource("/hello/hello/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    // 期待するDBレコード
    var expectedTestTable = expectedDataSet.getTable("test");
    // 比較
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

  // Helloテストに各種引き数を渡すproviderメソッド
  private static Stream<Arguments> helloTestProvider() {
    return Stream.of(
        // Stream型で各種パラメータを渡しテストを実行する
        // テストはパラメータ毎に管理され選択して実行できる
        Arguments.arguments("", """
            {
              "message": "Hello, world!"
            }
            """, "default"), Arguments.arguments("?name=naito", """
            {
              "message": "Hello, naito"
            }
            """, "naito")

    );

  }
}
