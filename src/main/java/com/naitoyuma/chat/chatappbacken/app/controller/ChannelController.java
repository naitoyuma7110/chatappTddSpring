package com.naitoyuma.chat.chatappbacken.app.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.naitoyuma.chat.chatappbacken.app.service.ChannelService;
import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/channels")
@Validated
public class ChannelController {

  // コンストラクタが 1 つしかない場合@Autowiredを省略しても constructor injection が実行される
  private final ChannelService channelService;

  @PostMapping()
  public Channel create(@Valid @RequestBody Channel channel) {
    return channelService.create(channel);
  }

  @GetMapping()
  public List<Channel> findAll() {
    return channelService.findAll();
  }

  @PutMapping("/{id}")
  public Channel update(@PathVariable("id") int id, @Valid @RequestBody Channel channel) {
    // @RestControllerによりリクエストのjsonデータはChannelに自動的にマッピングされインスタンス化される
    channel.setId(id);

    return channelService.update(channel);
  }
}
