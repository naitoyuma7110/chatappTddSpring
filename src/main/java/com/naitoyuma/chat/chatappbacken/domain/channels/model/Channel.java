package com.naitoyuma.chat.chatappbacken.domain.channels.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Channel {
  private int id;

  @NotBlank
  @Size(min = 1, max = 30, message = "Name must be between 1 and 10 characters")
  private String name;
}
