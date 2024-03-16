package com.naitoyuma.chat.chatappbacken.domain.channels.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class Channel {
  private int id;

  // @NotNull
  // @Size(min = 1, max = 20)
  private String name;
}
