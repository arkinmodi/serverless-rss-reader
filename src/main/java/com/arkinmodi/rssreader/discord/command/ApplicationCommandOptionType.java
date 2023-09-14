package com.arkinmodi.rssreader.discord.command;

public enum ApplicationCommandOptionType {
  SUB_COMMAND(1),
  SUB_COMMAND_GROUP(2),
  STRING(3),
  INTEGER(4),
  BOOLEAN(5),
  USER(6),
  CHANNEL(7),
  ROLE(8),
  MENTIONABLE(9),
  NUMBER(10),
  ATTACHMENT(11);

  public final int type;

  private ApplicationCommandOptionType(int type) {
    this.type = type;
  }
}
