package com.arkinmodi.rssreader.atomdocument;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AtomFeedDocument {
  private List<AtomEntryDocument> entries = new ArrayList<>();
  private String title;
  private String id;
  private Map<String, String> links = new HashMap<>(5);
  private Optional<AtomAuthor> author = Optional.empty();
  private Optional<ZonedDateTime> updated = Optional.empty();

  public List<AtomEntryDocument> getEntries() {
    return entries;
  }

  public void setEntries(List<AtomEntryDocument> entries) {
    this.entries = entries;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, String> getLinks() {
    return links;
  }

  public void setLinks(Map<String, String> link) {
    this.links = link;
  }

  public Optional<AtomAuthor> getAuthor() {
    return author;
  }

  public void setAuthor(Optional<AtomAuthor> author) {
    this.author = author;
  }

  public Optional<ZonedDateTime> getUpdated() {
    return updated;
  }

  public void setUpdated(Optional<ZonedDateTime> updated) {
    this.updated = updated;
  }
}
