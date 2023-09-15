package com.arkinmodi.rssreader.discord.command;

import com.google.gson.annotations.SerializedName;

public enum ApplicationCommandTypes {
  @SerializedName("1")
  CHAT_INPUT(1),

  @SerializedName("2")
  USER(2),

  @SerializedName("3")
  MESSAGE(3);

  private final int type;

  public int getRawValue() {
    return type;
  }

  private ApplicationCommandTypes(int type) {
    this.type = type;
  }
}
