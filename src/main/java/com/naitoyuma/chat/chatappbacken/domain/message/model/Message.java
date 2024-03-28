package com.naitoyuma.chat.chatappbacken.domain.message.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Message {
  private String id;

  private int channelId;

  // @NotNull(message = "Message Text is required")
  @Size(min = 1, max = 200, message = "Name must be between 1 and 200 characters")
  private String text;

  // @NotNull(message = "UserName is required")
  @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
  private String username;

  // @NotNull
  private LocalDateTime timestamp;
}
