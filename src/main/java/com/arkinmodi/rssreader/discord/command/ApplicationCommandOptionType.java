package com.arkinmodi.rssreader.discord.command;

import com.google.gson.annotations.SerializedName;

public enum ApplicationCommandOptionType {
  @SerializedName("1")
  SUB_COMMAND(1),

  @SerializedName("2")
  SUB_COMMAND_GROUP(2),

  @SerializedName("3")
  STRING(3),

  @SerializedName("4")
  INTEGER(4),

  @SerializedName("5")
  BOOLEAN(5),

  @SerializedName("6")
  USER(6),

  @SerializedName("7")
  CHANNEL(7),

  @SerializedName("8")
  ROLE(8),

  @SerializedName("9")
  MENTIONABLE(9),

  @SerializedName("10")
  NUMBER(10),

  @SerializedName("11")
  ATTACHMENT(11);

  public final int type;

  private ApplicationCommandOptionType(int type) {
    this.type = type;
  }
}
