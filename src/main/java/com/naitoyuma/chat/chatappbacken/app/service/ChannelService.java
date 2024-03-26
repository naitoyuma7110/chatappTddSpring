package com.naitoyuma.chat.chatappbacken.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;
import com.naitoyuma.chat.chatappbacken.domain.channels.service.ChannelDomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional // 自動でメソッドにトランザクション、例外時にロールバックが実行される
public class ChannelService {

  private final ChannelDomainService channelDomainService;

  public Channel create(Channel channel) {
    return channelDomainService.create(channel);
  }

  public List<Channel> findAll() {
    return channelDomainService.findAll();
  }

  public Channel update(Channel channel) {
    return channelDomainService.update(channel);
  }

}
