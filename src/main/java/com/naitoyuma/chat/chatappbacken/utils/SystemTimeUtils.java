package com.naitoyuma.chat.chatappbacken.utils;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class SystemTimeUtils {
  // システムのデフォルトのタイムゾーンを取得
  private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

  // 現在のローカル日時を取得
  public LocalDateTime getCurrentLocalDateTime() {
    return LocalDateTime.now();
  }

  // システムのデフォルトのタイムゾーンで指定されたローカル日時を取得
  public LocalDateTime getCurrentLocalDateTime(ZoneId zoneId) {
    return LocalDateTime.now(zoneId);
  }

  // 現在のUTC日時を取得
  public LocalDateTime getCurrentUtcDateTime() {
    return LocalDateTime.now(ZoneId.of("UTC"));
  }

  // 指定されたローカル日時をUTC日時に変換
  public LocalDateTime convertToUtcDateTime(LocalDateTime localDateTime) {
    return localDateTime.atZone(DEFAULT_ZONE).withZoneSameInstant(ZoneId.of("UTC"))
        .toLocalDateTime();
  }

  // 指定されたUTC日時をシステムのデフォルトのタイムゾーンの日時に変換
  public LocalDateTime convertToLocalDateTime(LocalDateTime utcDateTime) {
    return utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(DEFAULT_ZONE).toLocalDateTime();
  }
}
