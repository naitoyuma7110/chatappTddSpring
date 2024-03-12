package com.naitoyuma.chat.chatappbacken.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naitoyuma.chat.chatappbacken.app.service.ChannelService;
import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/channels")
public class ChannelController {

  // コンストラクタが 1 つしかない場合、コンストラクタの@Autowiredを省略しても constructor injection が実行される
  private final ChannelService channelService;

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
