package com.arkinmodi.rssreader.discord.command;

import java.util.List;

public class ApplicationCommand {

  ApplicationCommandTypes type;
  String name;
  String description;
  List<Options> options;

  public ApplicationCommandTypes getType() {
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
    return String.format(
        "ApplicationCommand[type=%s, name=%s, description=%s, options=%s]",
        type, name, description, options);
  }
}
