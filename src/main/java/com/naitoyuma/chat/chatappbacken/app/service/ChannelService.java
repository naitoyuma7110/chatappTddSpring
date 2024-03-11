package com.naitoyuma.chat.chatappbacken.app.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

@Service
@Transactional // 自動でメソッドにトランザクション、例外時のロールバックが実行される
public class ChannelService {

  public Channel create(Channel channel) {
    return channel;
  }

  public List<Channel> findAll() {
    return Collections.emptyList();
  }

}
