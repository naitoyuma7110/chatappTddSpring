package com.naitoyuma.chat.chatappbacken.api;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageApiTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void postTest() throws Exception {
    // APIのモック化と検証
    mockMvc.perform(MockMvcRequestBuilders.post("/messages/1").content("""
        {
          "text": "メッセージ本文",
          "username": "naito"
        }
        """).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect((result) -> JSONAssert.assertEquals("""
            {
                "id": "",
                "channelId": 1,
                "text": "メッセージ本文",
                "username": "naito",
                "timestamp": ""
            }
            """, result.getResponse().getContentAsString(), new CustomComparator(
            // 余分なJSON項目が含まれていてもOKとする
            JSONCompareMode.LENIENT,
            // idのフォーマットを正規表現でチェック
            new Customization("id",
                new RegularExpressionValueMatcher<>("\\d{12}-[a-zA-Z0-9\\-]{36}")),
            // timestampは検証対象外とする(どんな値でもOK)
            new Customization("timestamp", (o1, o2) -> true))));

  }



}
