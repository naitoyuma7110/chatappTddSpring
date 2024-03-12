package com.naitoyuma.chat.chatappbacken.infrastructure;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;
import com.naitoyuma.chat.chatappbacken.domain.service.ChannelRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisChannelRepository implements ChannelRepository {

  @Override
  public void insert(Channel channel) {

  }

  @Override
  public List<Channel> findAll() {
    return null;
  }

  @Override
  public Optional<Integer> getMaxId() {

    return Optional.empty();
  }
}
