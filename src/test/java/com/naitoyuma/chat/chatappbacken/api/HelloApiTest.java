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

@SpringBootTest
@AutoConfigureMockMvc
public class HelloApiTest {
  // この2行でテスト用のコントローラーを含むMVC環境をMockMvcとしてインスタント化しテストで使用可能にする…
  @Autowired
  private MockMvc mockMvc;

  // Controllerのモックを使用しリクエストとレスポンスの検証を行う(modelやserviceなど他レイヤーとは切り離されている)
  @Test
  public void helloTest() throws Exception {
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
  }

}
