package com.arkinmodi.rssreader.xmlhandler;

import com.arkinmodi.rssreader.atomdocument.AtomAuthor;
import com.arkinmodi.rssreader.atomdocument.AtomEntryDocument;
import com.arkinmodi.rssreader.atomdocument.AtomFeedDocument;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AtomHandler extends DefaultHandler {

  private AtomFeedDocument feed = new AtomFeedDocument();
  private AtomEntryDocument entry;
  private AtomAuthor author;

  private StringBuilder data;
  private String linkRel = "";

  private boolean isAuthor = false;
  private boolean isEntry = false;

  public AtomFeedDocument getFeed() {
    return feed;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if ("link".equalsIgnoreCase(qName)) {
      linkRel = attributes.getValue("rel");

    } else if ("author".equalsIgnoreCase(qName)) {
      author = new AtomAuthor();
      isAuthor = true;

    } else if ("entry".equalsIgnoreCase(qName)) {
      entry = new AtomEntryDocument();
      isEntry = true;

    } else if (isEntry && "content".equalsIgnoreCase(qName)) {
      entry.setContentType(Optional.ofNullable(attributes.getValue("type")));
    }

    data = new StringBuilder();
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if ("id".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entry.setId(data.toString().strip());
      } else {
        feed.setId(data.toString().strip());
      }

    } else if (linkRel != null && "link".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entry.getLinks().put(linkRel, data.toString().strip());
      } else {
        feed.getLinks().put(linkRel, data.toString().strip());
      }

    } else if ("title".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entry.setTitle(data.toString().strip());
      } else {
        feed.setTitle(data.toString().strip());
      }

    } else if ("updated".equalsIgnoreCase(qName)) {
      ZonedDateTime date = ZonedDateTime.parse(data.toString());
      if (isEntry) {
        entry.setUpdated(Optional.of(date));
      } else {
        feed.setUpdated(Optional.of(date));
      }

    } else if (isEntry && "published".equalsIgnoreCase(qName)) {
      ZonedDateTime date = ZonedDateTime.parse(data.toString());
      entry.setPublished(Optional.of(date));

    } else if (isEntry && "content".equalsIgnoreCase(qName)) {
      entry.setContent(Optional.of(data.toString()));

    } else if (isEntry && "summary".equalsIgnoreCase(qName)) {
      entry.setSummary(Optional.of(data.toString().strip()));

    } else if ("entry".equalsIgnoreCase(qName)) {
      feed.getEntries().add(entry);
      isEntry = false;

    } else if ("author".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entry.setAuthor(Optional.of(author));
      } else {
        feed.setAuthor(Optional.of(author));
      }
      isAuthor = false;

    } else if (isAuthor && "name".equalsIgnoreCase(qName)) {
      author.setName(data.toString().strip());

    } else if (isAuthor && "email".equalsIgnoreCase(qName)) {
      author.setEmail(Optional.of(data.toString().strip()));

    } else if (isAuthor && "uri".equalsIgnoreCase(qName)) {
      author.setUri(Optional.of(data.toString().strip()));
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    data.append(new String(ch, start, length));
  }
}
