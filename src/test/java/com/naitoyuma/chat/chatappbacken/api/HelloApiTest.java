package com.naitoyuma.chat.chatappbacken.api;

import org.junit.jupiter.api.Test;
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

// import jakarta.activation.DataSource;
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

  @Test
  public void helloTest() throws Exception {

    // Given、テスト実行に必要なレコードをDBのモックにセットする
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/hello/hello/default/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // Controllerのモックを使用しリクエストとレスポンスの検証を行う(modelやserviceなど他レイヤーとは切り離されている)
    mockMvc.perform(
        MockMvcRequestBuilders.get("/hello?name=naitoyuma")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals("""
            {
              "message": "Hello, naitoyuma"
            }
            """,
            result.getResponse().getContentAsString(),
            false));

    // DBのモックを使用しDBデータの整合性の検証を行う(Controllerやserviceなど他レイヤーとは切り離されている)
    var actualDataSet = databaseTester.getConnection().createDataSet();
    // テスト実行後のDBレコード
    var actualTestTable = actualDataSet.getTable("test");
    var expectedUrl = this.getClass().getResource("/hello/hello/default/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    // 期待するDBレコード
    var expectedTestTable = expectedDataSet.getTable("test");
    // 比較
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

}
