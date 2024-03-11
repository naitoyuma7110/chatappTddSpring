package com.naitoyuma.chat.chatappbacken.app.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naitoyuma.chat.chatappbacken.app.service.ChannelService;
import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

@RestController
@RequestMapping("/channels")
@CrossOrigin
public class ChannelController {

  private final ChannelService channelService;

  public ChannelController(ChannelService channelService) {
    this.channelService = channelService;
  }

  @PostMapping()
  public Channel create(@RequestBody Channel channel) {
    // TODO: Serviceを作成してビジネスロジックを実装するまでそのままchannelを返す
    return channelService.create(channel);
  }

  @GetMapping()
  public List<Channel> findAll() {
    // TODO: Serviceを作成するまでは空のリストを返す
    return channelService.findAll();
  }
}
