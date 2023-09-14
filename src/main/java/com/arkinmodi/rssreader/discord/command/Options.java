package com.arkinmodi.rssreader.discord.command;

import java.util.List;

public class Options {
  ApplicationCommandOptionType type;
  String name;
  String description;
  boolean required;
  List<Choices> choices;

  public ApplicationCommandOptionType getType() {
    return type;
  }

  public void setType(ApplicationCommandOptionType type) {
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

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public List<Choices> getChoices() {
    return choices;
  }

  public void setChoices(List<Choices> choices) {
    this.choices = choices;
  }

  public String toString() {
    return String.format(
        "Options[name=%s, description=%s, required=%b choices=%s]",
        name, description, required, choices);
  }
}
