package com.arkinmodi.rssreader.document.atom;

import java.util.Optional;

public class AtomAuthor {
  private Optional<String> email;
  private Optional<String> uri;
  private String name;

  public String getName() {
    return name;
  }

  public Optional<String> getEmail() {
    return email;
  }

  public Optional<String> getUri() {
    return uri;
  }

  private AtomAuthor(AtomAuthorBuilder builder) {
    this.email = builder.email;
    this.name = builder.name;
    this.uri = builder.uri;
  }

  public static class AtomAuthorBuilder {
    private String name;
    private Optional<String> email = Optional.empty();
    private Optional<String> uri = Optional.empty();

    public AtomAuthorBuilder name(String name) {
      this.name = name;
      return this;
    }

    public AtomAuthorBuilder email(String email) {
      this.email = Optional.of(email);
      return this;
    }

    public AtomAuthorBuilder uri(String uri) {
      this.uri = Optional.of(uri);
      return this;
    }

    public AtomAuthor build() {
      return new AtomAuthor(this);
    }
  }
}
