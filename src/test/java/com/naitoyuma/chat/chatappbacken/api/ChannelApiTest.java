package com.naitoyuma.chat.chatappbacken.api;

import java.util.stream.Stream;
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

  @DisplayName("【GET】正常系")
  @Test
  public void channelGetTest() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/channels"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals("""
              []
            """, result.getResponse().getContentAsString(), false));
  }

  // 正常系
  @DisplayName("【POST】正常系")
  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("channelTestProvider")
  public void channelPostTest(String queryString, String expectedBody) throws Exception {

    // APIのモック化
    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(queryString)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect((result) -> JSONAssert
            .assertEquals(expectedBody, result.getResponse().getContentAsString(), false));
  }

  // channelPostTestに各種引き数を渡すproviderメソッド
  private static Stream<Arguments> channelTestProvider() {
    return Stream.of(
        // Stream型で各種パラメータを渡しテストを実行する
        // テストはパラメータ毎に管理され選択して実行できる
        Arguments.arguments("""
            {
                "name": "1回目のcreateテスト"
            }
            """, """
            {
                "id": 1,
                "name": "1回目のcreateテスト"
            }
            """), Arguments.arguments("""
            {
                "name": "2回目のcreateテスト"
            }
            """, """
            {
                "id": 2,
                "name": "2回目のcreateテスト"
            }
            """), Arguments.arguments("""
            {
                "name": "3回目のcreateテスト"
            }
            """, """
            {
                "id": 3,
                "name": "3回目のcreateテスト"
            }
            """), Arguments.arguments("""
            {
                "id": 5,
                "hello":"Hi!",
                "name": "3回目のcreateテスト"
            }
            """, """
            {
                "id": 4,
                "name": "3回目のcreateテスト"
            }
            """));
  }

  // 異常系
  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("invalidChannelTestProvider")
  public void invalidChannelPostTest(String queryString, String invalidRequestBody)
      throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.post("/channels").content(queryString)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect((result) -> JSONAssert
            .assertEquals(invalidRequestBody, result.getResponse().getContentAsString(), false));
  }


  private static Stream<Arguments> invalidChannelTestProvider() {
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
