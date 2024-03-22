package com.naitoyuma.chat.chatappbacken.domain.channels.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Channel {

  private int id;

  @NotNull(message = "Name is required")
  @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
  private String name;
}
