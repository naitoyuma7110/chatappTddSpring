package com.naitoyuma.chat.chatappbacken.domain.channels.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Channel {
  private int id;

  @NotBlank
  @Size(min = 1, max = 30)
  private String name;
}
