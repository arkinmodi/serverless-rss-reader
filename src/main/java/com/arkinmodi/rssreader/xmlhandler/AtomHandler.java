package com.arkinmodi.rssreader.xmlhandler;

import com.arkinmodi.rssreader.document.atom.AtomAuthor.AtomAuthorBuilder;
import com.arkinmodi.rssreader.document.atom.AtomEntryDocument.AtomEntryDocumentBuilder;
import com.arkinmodi.rssreader.document.atom.AtomFeedDocument;
import com.arkinmodi.rssreader.document.atom.AtomFeedDocument.AtomFeedDocumentBuilder;
import java.time.ZonedDateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AtomHandler extends DefaultHandler {

  private AtomAuthorBuilder authorBuilder;
  private AtomEntryDocumentBuilder entryBuilder;
  private AtomFeedDocument feed;
  private AtomFeedDocumentBuilder feedBuilder = new AtomFeedDocumentBuilder();
  private StringBuilder data;
  private boolean isAuthor = false;
  private boolean isEntry = false;

  public AtomFeedDocument getFeed() {
    if (feed == null) {
      feed = feedBuilder.build();
    }
    return feed;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if ("link".equalsIgnoreCase(qName)) {
      String rel = attributes.getValue("rel");
      String href = attributes.getValue("href");

      if (rel != null && href != null) {
        if (isEntry) {
          entryBuilder.link(rel, href);
        } else {
          feedBuilder.link(rel, href);
        }
      }

    } else if ("author".equalsIgnoreCase(qName)) {
      authorBuilder = new AtomAuthorBuilder();
      isAuthor = true;

    } else if ("entry".equalsIgnoreCase(qName)) {
      entryBuilder = new AtomEntryDocumentBuilder();
      isEntry = true;

    } else if (isEntry && "content".equalsIgnoreCase(qName)) {
      entryBuilder.contentType(attributes.getValue("type"));
    }

    data = new StringBuilder();
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if ("id".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entryBuilder.id(data.toString().strip());
      } else {
        feedBuilder.id(data.toString().strip());
      }

    } else if ("title".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entryBuilder.title(data.toString().strip());
      } else {
        feedBuilder.title(data.toString().strip());
      }

    } else if ("updated".equalsIgnoreCase(qName)) {
      ZonedDateTime date = ZonedDateTime.parse(data.toString());
      if (isEntry) {
        entryBuilder.updated(date);
      } else {
        feedBuilder.updated(date);
      }

    } else if (isEntry && "published".equalsIgnoreCase(qName)) {
      ZonedDateTime date = ZonedDateTime.parse(data.toString());
      entryBuilder.published(date);

    } else if (isEntry && "content".equalsIgnoreCase(qName)) {
      entryBuilder.content(data.toString());

    } else if (isEntry && "summary".equalsIgnoreCase(qName)) {
      entryBuilder.summary(data.toString().strip());

    } else if ("entry".equalsIgnoreCase(qName)) {
      feedBuilder.entry(entryBuilder.build());
      isEntry = false;

    } else if ("author".equalsIgnoreCase(qName)) {
      if (isEntry) {
        entryBuilder.author(authorBuilder.build());
      } else {
        feedBuilder.author(authorBuilder.build());
      }
      isAuthor = false;

    } else if (isAuthor && "name".equalsIgnoreCase(qName)) {
      authorBuilder.name(data.toString().strip());

    } else if (isAuthor && "email".equalsIgnoreCase(qName)) {
      authorBuilder.email(data.toString().strip());

    } else if (isAuthor && "uri".equalsIgnoreCase(qName)) {
      authorBuilder.uri(data.toString().strip());
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    data.append(new String(ch, start, length));
  }
}
