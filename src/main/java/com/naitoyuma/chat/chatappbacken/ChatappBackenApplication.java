package com.naitoyuma.chat.chatappbacken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplicationが付与されたクラスのパッケージ配下を探索対象としBeanとして管理する
@SpringBootApplication
public class ChatappBackenApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChatappBackenApplication.class, args);
  }

}
