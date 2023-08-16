package com.arkinmodi.rssreader.atomdocument;

import java.util.Optional;

public class AtomAuthor {
  private String name;
  private Optional<String> email = Optional.empty();
  private Optional<String> uri = Optional.empty();

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Optional<String> getEmail() {
    return email;
  }
  public void setEmail(Optional<String> email) {
    this.email = email;
  }
  public Optional<String> getUri() {
    return uri;
  }
  public void setUri(Optional<String> uri) {
    this.uri = uri;
  }
}
