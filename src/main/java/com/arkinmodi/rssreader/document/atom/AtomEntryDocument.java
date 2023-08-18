package com.arkinmodi.rssreader.document.atom;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AtomEntryDocument {
  private Map<String, String> links;
  private Optional<AtomAuthor> author;
  private Optional<String> content;
  private Optional<String> contentType;
  private Optional<String> summary;
  private Optional<ZonedDateTime> published;
  private Optional<ZonedDateTime> updated;
  private String id;
  private String title;

  public String getId() {
    return id;
  }

  public Optional<ZonedDateTime> getUpdated() {
    return updated;
  }

  public Map<String, String> getLinks() {
    return links;
  }

  public String getTitle() {
    return title;
  }

  public Optional<ZonedDateTime> getPublished() {
    return published;
  }

  public Optional<String> getContent() {
    return content;
  }

  public Optional<String> getContentType() {
    return contentType;
  }

  public Optional<AtomAuthor> getAuthor() {
    return author;
  }

  public Optional<String> getSummary() {
    return summary;
  }

  private AtomEntryDocument(AtomEntryDocumentBuilder builder) {
    this.author = builder.author;
    this.content = builder.content;
    this.contentType = builder.contentType;
    this.id = builder.id;
    this.links = builder.links;
    this.published = builder.published;
    this.summary = builder.summary;
    this.title = builder.title;
    this.updated = builder.updated;
  }

  public static class AtomEntryDocumentBuilder {
    private Map<String, String> links = new HashMap<>(5);
    private Optional<AtomAuthor> author = Optional.empty();
    private Optional<String> content = Optional.empty();
    private Optional<String> contentType = Optional.empty();
    private Optional<String> summary = Optional.empty();
    private Optional<ZonedDateTime> published = Optional.empty();
    private Optional<ZonedDateTime> updated;
    private String id;
    private String title;

    public AtomEntryDocumentBuilder id(String id) {
      this.id = id;
      return this;
    }

    public AtomEntryDocumentBuilder updated(ZonedDateTime updated) {
      this.updated = Optional.of(updated);
      return this;
    }

    public AtomEntryDocumentBuilder link(String rel, String href) {
      this.links.put(rel, href);
      return this;
    }

    public AtomEntryDocumentBuilder title(String title) {
      this.title = title;
      return this;
    }

    public AtomEntryDocumentBuilder published(ZonedDateTime published) {
      this.published = Optional.of(published);
      return this;
    }

    public AtomEntryDocumentBuilder content(String content) {
      this.content = Optional.of(content);
      return this;
    }

    public AtomEntryDocumentBuilder contentType(String contentType) {
      this.contentType = Optional.ofNullable(contentType);
      return this;
    }

    public AtomEntryDocumentBuilder author(AtomAuthor author) {
      this.author = Optional.of(author);
      return this;
    }

    public AtomEntryDocumentBuilder summary(String summary) {
      this.summary = Optional.of(summary);
      return this;
    }

    public AtomEntryDocument build() {
      return new AtomEntryDocument(this);
    }
  }
}
