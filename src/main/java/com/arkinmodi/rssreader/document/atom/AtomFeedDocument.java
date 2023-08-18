package com.arkinmodi.rssreader.document.atom;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AtomFeedDocument {
  private List<AtomEntryDocument> entries;
  private Map<String, String> links;
  private Optional<AtomAuthor> author;
  private Optional<ZonedDateTime> updated;
  private String id;
  private String title;

  public List<AtomEntryDocument> getEntries() {
    return entries;
  }

  public String getTitle() {
    return title;
  }

  public String getId() {
    return id;
  }

  public Map<String, String> getLinks() {
    return links;
  }

  public Optional<AtomAuthor> getAuthor() {
    return author;
  }

  public Optional<ZonedDateTime> getUpdated() {
    return updated;
  }

  private AtomFeedDocument(AtomFeedDocumentBuilder builder) {
    this.author = builder.author;
    this.entries = builder.entries;
    this.id = builder.id;
    this.links = builder.links;
    this.title = builder.title;
    this.updated = builder.updated;
  }

  public static class AtomFeedDocumentBuilder {
    private List<AtomEntryDocument> entries = new ArrayList<>();
    private Map<String, String> links = new HashMap<>(5);
    private Optional<AtomAuthor> author = Optional.empty();
    private Optional<ZonedDateTime> updated = Optional.empty();
    private String id;
    private String title;

    public AtomFeedDocumentBuilder entry(AtomEntryDocument entry) {
      this.entries.add(entry);
      return this;
    }

    public AtomFeedDocumentBuilder title(String title) {
      this.title = title;
      return this;
    }

    public AtomFeedDocumentBuilder id(String id) {
      this.id = id;
      return this;
    }

    public AtomFeedDocumentBuilder link(String rel, String href) {
      this.links.put(rel, href);
      return this;
    }

    public AtomFeedDocumentBuilder author(AtomAuthor author) {
      this.author = Optional.of(author);
      return this;
    }

    public AtomFeedDocumentBuilder updated(ZonedDateTime updated) {
      this.updated = Optional.of(updated);
      return this;
    }

    public AtomFeedDocument build() {
      return new AtomFeedDocument(this);
    }
  }
}
