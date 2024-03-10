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
  @Test
  public void channelPostTest() throws Exception {

    String jsonPayload = """
        {
            "id": 1,
            "name": "テスト"
        }
        """;

    mockMvc.perform(
        MockMvcRequestBuilders.post("/channels")
            .content(jsonPayload)
            .contentType(
                MediaType.APPLICATION_JSON)
            .accept(
                MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals(
            """
                {
                    "id": 1,
                    "name": "テスト"
                }
                """,
            result.getResponse().getContentAsString(),
            false));

  }

}
