package com.arkinmodi.rssreader.discord.command;

import java.util.List;

public class ApplicationCommand {

  private ApplicationCommandTypes type;
  private String name;
  private String description;
  private List<Options> options;

  public ApplicationCommandTypes getType() {
    if (type == null) {
      type = ApplicationCommandTypes.CHAT_INPUT;
    }
    return type;
  }

  public void setType(ApplicationCommandTypes type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Options> getOptions() {
    return options;
  }

  public void setOptions(List<Options> options) {
    this.options = options;
  }

  public String toString() {
    return "ApplicationCommand[type=%s, name=%s, description=%s, options=%s]"
        .formatted(type, name, description, options);
  }
}
