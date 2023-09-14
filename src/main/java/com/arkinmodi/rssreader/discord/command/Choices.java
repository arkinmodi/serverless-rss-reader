package com.arkinmodi.rssreader.discord.command;

public class Choices {
  String name;
  String value;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String toString() {
    return String.format("Choices[name=%s, value=%s]", name, value);
  }
}
