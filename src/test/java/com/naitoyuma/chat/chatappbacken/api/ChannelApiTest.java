package com.naitoyuma.chat.chatappbacken.api;

import java.util.stream.Stream;
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

  @Test
  public void channelGetTest() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/channels"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals(
            """
                  []
                """,
            result.getResponse().getContentAsString(),
            false));
  }

  @SuppressWarnings("null")
  @ParameterizedTest
  @MethodSource("channelTestProvider")
  public void channelPostTest(String queryString, String expectedBody) throws Exception {

    mockMvc.perform(
        MockMvcRequestBuilders.post("/channels")
            .content(
                queryString)
            .contentType(
                MediaType.APPLICATION_JSON)
            .accept(
                MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals(
            expectedBody,
            result.getResponse().getContentAsString(),
            false));
  }

  // Helloテストに各種引き数を渡すproviderメソッド
  private static Stream<Arguments> channelTestProvider() {
    return Stream.of(
        // Stream型で各種パラメータを渡しテストを実行する
        // テストはパラメータ毎に管理され選択して実行できる
        Arguments.arguments(
            """
                {
                    "name": "テスト"
                }
                """,
            """
                {
                    "id": 1,
                    "name": "テスト"
                }
                """)

    );

  }

}
