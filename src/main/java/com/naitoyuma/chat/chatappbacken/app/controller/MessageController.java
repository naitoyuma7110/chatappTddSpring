package com.naitoyuma.chat.chatappbacken.app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.naitoyuma.chat.chatappbacken.domain.message.model.Message;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

  @PostMapping("/{channelId}")
  public Message create(@PathVariable int channelId, @RequestBody Message message) {
    message.setChannelId(channelId);
    return message;
  }


}
