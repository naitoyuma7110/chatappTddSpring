package com.naitoyuma.chat.chatappbacken.infrastructure.channels;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.naitoyuma.chat.chatappbacken.domain.channels.model.Channel;

// MyBatisに対する操作メソッドを定義する
@Mapper
public interface ChannelMapper {
  void insert(Channel channel);

  List<Channel> findAll();

  Optional<Integer> getMaxId();
}
