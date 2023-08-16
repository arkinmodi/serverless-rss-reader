package com.arkinmodi.rssreader.document.atom;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AtomEntryDocument {
  private String id;
  private Optional<ZonedDateTime> updated;
  private Map<String, String> links = new HashMap<>(5);
  private String title;
  private Optional<ZonedDateTime> published = Optional.empty();
  private Optional<String> content = Optional.empty();
  private Optional<String> contentType = Optional.empty();
  private Optional<AtomAuthor> author = Optional.empty();
  private Optional<String> summary = Optional.empty();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Optional<ZonedDateTime> getUpdated() {
    return updated;
  }

  public void setUpdated(Optional<ZonedDateTime> updated) {
    this.updated = updated;
  }

  public Map<String, String> getLinks() {
    return links;
  }

  public void setLinks(Map<String, String> link) {
    this.links = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Optional<ZonedDateTime> getPublished() {
    return published;
  }

  public void setPublished(Optional<ZonedDateTime> published) {
    this.published = published;
  }

  public Optional<String> getContent() {
    return content;
  }

  public void setContent(Optional<String> content) {
    this.content = content;
  }

  public Optional<String> getContentType() {
    return contentType;
  }

  public void setContentType(Optional<String> contentType) {
    this.contentType = contentType;
  }

  public Optional<AtomAuthor> getAuthor() {
    return author;
  }

  public void setAuthor(Optional<AtomAuthor> author) {
    this.author = author;
  }

  public Optional<String> getSummary() {
    return summary;
  }

  public void setSummary(Optional<String> summary) {
    this.summary = summary;
  }
}
