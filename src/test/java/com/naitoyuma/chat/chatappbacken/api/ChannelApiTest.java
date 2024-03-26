package com.naitoyuma.chat.chatappbacken.api;

import java.net.URL;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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



@SpringBootTest
@AutoConfigureMockMvc
public class ChannelApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;

  @DisplayName("【GET】正常系")
  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("channelGetTestProvider")
  public void channelGetTest(String expectedBody, String dbPath) throws Exception {

    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/channels/findAll/" + dbPath + "/given/");
    // setDataSetでレコードが挿入されるテーブルは"table-ordering.txt"に順番で記載
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    mockMvc
        .perform(MockMvcRequestBuilders.get("/channels").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));

    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualTestTable = actualDataSet.getTable("channels");
    URL expectedUrl = this.getClass().getResource("/channels/findAll/" + dbPath + "/given/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    var expectedTestTable = expectedDataSet.getTable("channels");
    // DBの変更は行われない
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

  // channelPostTestに各種引き数を渡すproviderメソッド
  private static Stream<Arguments> channelGetTestProvider() {
    return Stream.of(
        // Stream型で各種パラメータを渡しテストを実行する
        // テストはパラメータ毎に管理され選択して実行できる
        Arguments.arguments("""
            [
              {
              "id": 1,
              "name": "1つ目のチャンネル"
              },
              {
              "id": 2,
              "name": "2つ目のチャンネル"
              },
              {
              "id": 3,
              "name": "既にDBに2件のチャンネルが存在する"
              }
            ]
            """, "alreadyExist"));
  }

  // 正常系
  @DisplayName("【POST】正常系")
  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("channelPostTestProvider")
  public void channelPostTest(String queryString, String expectedBody, String dbPath)
      throws Exception {

    // Given:テスト実行に必要なレコードをDBのモックにセットする
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/channels/create/" + dbPath + "/given/");
    // setDataSetでレコードが挿入されるテーブルは"table-ordering.txt"に順番で記載
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // APIのモック化
    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(queryString)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));

    // DBのモックを使用しDBデータの整合性の検証を行う(Controllerやserviceなど他レイヤーとは切り離されている)
    var actualDataSet = databaseTester.getConnection().createDataSet();
    // テスト実行後のDBレコード
    var actualTestTable = actualDataSet.getTable("channels");

    URL expectedUrl = this.getClass().getResource("/channels/create/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    // 期待するDBレコード
    var expectedTestTable = expectedDataSet.getTable("channels");
    // 比較
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

  // channelPostTestに各種引き数を渡すproviderメソッド
  private static Stream<Arguments> channelPostTestProvider() {
    return Stream.of(
        // Stream型で各種パラメータを渡しテストを実行する
        // テストはパラメータ毎に管理され選択して実行できる
        Arguments.arguments("""
            {
                "name": "1つ目のチャンネル"
            }
            """, """
            {
                "id": 1,
                "name": "1つ目のチャンネル"
            }
            """, "default"), Arguments.arguments("""
            {
                "name": "既にDBに2件のチャンネルが存在する"
            }
            """, """
            {
                "id": 3,
                "name": "既にDBに2件のチャンネルが存在する"
            }
            """, "alreadyExist"));
  }

  // 異常系
  @DisplayName("【POST】異常系")
  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("invalidChannelPostTestProvider")
  public void invalidChannelPostTest(String queryString, String invalidRequestBody)
      throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(queryString)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect((result) -> JSONAssert
            .assertEquals(invalidRequestBody, result.getResponse().getContentAsString(), false));
  }


  private static Stream<Arguments> invalidChannelPostTestProvider() {
    return Stream.of(

        Arguments.arguments("""
            {
                "name": "30文字以上のチャンネル名です。30文字以上のチャンネル名です。30文字以上のチャンネル名です。30文字以上のチャンネル名です。"
            }
            """, """
            {
              "status": 400,
              "errors": [
              {
                "field": "name",
                "message": "Name must be between 1 and 30 characters"
              }
            ]
            }
            """), Arguments.arguments("""
            {
            }
            """, """
            {
              "status": 400,
              "errors": [
              {
                "field": "name",
                "message": "Name is required"
              }
            ]
            }
            """), Arguments.arguments("""
            {}
            """, """
            {
              "status": 400,
              "errors": [
              {
                "field": "name",
                "message": "Name is required"
              }
            ]
            }
            """));
  }
}
