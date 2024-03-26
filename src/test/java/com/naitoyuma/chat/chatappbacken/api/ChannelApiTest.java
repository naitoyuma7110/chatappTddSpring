package com.naitoyuma.chat.chatappbacken.api;

import java.net.URL;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.api.DisplayName;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



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
  @MethodSource("getTestProvider")
  public void getTest(String expectedBody, String dbPath) throws Exception {

    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/channels/findAll/" + dbPath + "/given/");
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
    // DBの変更は行われないが他テストとの統一性をもたせるため記載
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

  private static Stream<Arguments> getTestProvider() {
    return Stream.of(Arguments.arguments("""
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
  @MethodSource("postTestProvider")
  public void postTest(String queryString, String expectedBody, String dbPath) throws Exception {

    // Given:テスト実行に必要なレコードをDBのモックにセットする
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/channels/create/" + dbPath + "/given/");
    // setDataSetでレコードが挿入されるテーブルは"table-ordering.txt"に順番で記載
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // APIのモック化と検証
    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(queryString)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));

    // expected:DB内容を検証
    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualTestTable = actualDataSet.getTable("channels");
    URL expectedUrl = this.getClass().getResource("/channels/create/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    var expectedTestTable = expectedDataSet.getTable("channels");
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

  // postTestに各種引き数を渡すproviderメソッド
  private static Stream<Arguments> postTestProvider() {
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
  @MethodSource("invalidPostTestProvider")
  public void invalidPostTest(String queryString, String invalidRequestBody) throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(queryString)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect((result) -> JSONAssert
            .assertEquals(invalidRequestBody, result.getResponse().getContentAsString(), false));
  }


  private static Stream<Arguments> invalidPostTestProvider() {
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

  // 正常系
  @DisplayName("【UPDATE】正常系")
  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("updateTestProvider")
  public void updateTest(int id, String requestBody, String dbPath) throws Exception {

    // Given:テスト実行に必要なレコードをDBのモックにセットする
    IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
    URL givenUrl = this.getClass().getResource("/channels/update/" + dbPath + "/given/");
    databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
    databaseTester.onSetup();

    // ObjectMapper:jsonとJavaオブジェクトの互換性を提供する
    // 期待されるレスポンスを作成、idとrequestBodyを含んだjson
    var expectedBodyMapper = new ObjectMapper();
    var expectedNode = expectedBodyMapper.readTree(requestBody);
    ((ObjectNode) expectedNode).put("id", id);
    var expectedBody = expectedNode.toString();

    // APIのモック化と検証
    mockMvc
        .perform(MockMvcRequestBuilders.put("/channels/" + id).content(requestBody)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));

    // expected:DB内容を検証
    var actualDataSet = databaseTester.getConnection().createDataSet();
    var actualTestTable = actualDataSet.getTable("channels");
    URL expectedUrl = this.getClass().getResource("/channels/update/" + dbPath + "/expected/");
    var expectedDataSet = new CsvURLDataSet(expectedUrl);
    var expectedTestTable = expectedDataSet.getTable("channels");
    Assertion.assertEquals(expectedTestTable, actualTestTable);
  }

  private static Stream<Arguments> updateTestProvider() {
    return Stream.of(Arguments.arguments(3, """
        {
            "name": "更新された3つ目のチャンネル"
        }
        """, "default"));
  }
}
