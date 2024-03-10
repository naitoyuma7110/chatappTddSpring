package com.naitoyuma.chat.chatappbacken.domain.channels.model;

import lombok.Data;

@Data
public class Channel {
  private int id;
  private String name;
}

// @Data:getter,setter,equal,hashCodeの省略(本来は下記のコード量)

/*
 * public class Channel {
 * public Channel(int id, String name) {
 * this.id = id;
 * this.name = name;
 * }
 * 
 * private int id;
 * private String name;
 * 
 * public int getId() {
 * return id;
 * }
 * 
 * public void setId(int id) {
 * this.id = id;
 * }
 * 
 * // idをハッシュ化して返す
 * 
 * @Override
 * public int hashCode() {
 * final int prime = 31;
 * int result = 1;
 * result = prime * result + id;
 * result = prime * result + ((name == null) ? 0 : name.hashCode());
 * return result;
 * }
 * 
 * // 同値性の判定
 * 
 * @Override
 * public boolean equals(Object obj) {
 * // 等価性を持つ場合はTrue
 * if (this == obj)
 * return true;
 * if (obj == null)
 * return false;
 * if (getClass() != obj.getClass())
 * return false;
 * Channel other = (Channel) obj;
 * if (id != other.id)
 * return false;
 * if (name == null) {
 * if (other.name != null)
 * return false;
 * } else if (!name.equals(other.name))
 * return false;
 * // 1. nullではない
 * // 2. クラスが一致
 * // 3. 同値のidを持つ
 * // 4. 同値のnameを持つ
 * // 上記がそろって同値
 * return true;
 * }
 * 
 * public String getName() {
 * return name;
 * }
 * 
 * public void setName(String name) {
 * this.name = name;
 * }
 * }
 */