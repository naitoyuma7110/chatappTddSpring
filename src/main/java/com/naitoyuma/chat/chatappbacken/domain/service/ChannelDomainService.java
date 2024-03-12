package com.naitoyuma.chat.chatappbacken.domain.service;

import java.util.Optional;
import java.util.List;
import java.util.Collections;
import org.springframework.stereotype.Service;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelDomainService {

  public Channel create(Channel channel) {

    // Optional型：Nullable
    Optional<Integer> currentMaxId = Optional.of(0); // 1を持つOptional型オブジェクトを生成

    var newId = currentMaxId.orElse(0) + 1;
    channel.setId(newId);
    // TODO: DBへの永続化

    return channel;
  }

  public List<Channel> findAll() {
    return Collections.emptyList();
  }

}
