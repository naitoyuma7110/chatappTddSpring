package com.naitoyuma.chat.chatappbacken.infrastructure.channels;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;
import com.naitoyuma.chat.chatappbacken.domain.channels.service.ChannelRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyBatisChannelRepository implements ChannelRepository {

  private final ChannelMapper channelMapper;

  @Override
  public void insert(Channel channel) {
    channelMapper.insert(channel);
  }

  @Override
  public List<Channel> findAll() {
    return channelMapper.findAll();
  }

  @Override
  public Optional<Integer> getMaxId() {
    return channelMapper.getMaxId();
  }

  @Override
  public void update(Channel channel) {
    channelMapper.update(channel);
  }
}
