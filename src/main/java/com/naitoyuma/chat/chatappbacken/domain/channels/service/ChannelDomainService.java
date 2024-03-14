package com.naitoyuma.chat.chatappbacken.domain.channels.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;


import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelDomainService {

  private final ChannelRepository channelRepository;


  public Channel create(Channel channel) {

    // Optional型：Nullable
    Optional<Integer> currentMaxId = channelRepository.getMaxId();

    var newId = currentMaxId.orElse(0) + 1;
    channel.setId(newId);

    channelRepository.insert(channel);

    return channel;
  }

  public List<Channel> findAll() {
    return Collections.emptyList();
  }

}
