package com.naitoyuma.chat.chatappbacken.domain.channels.service;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

import java.util.List;
import java.util.Optional;


public interface ChannelRepository {
  void insert(Channel channel);

  List<Channel> findAll();

  Optional<Integer> getMaxId();

  void update(Channel channel);

}
