package com.arkinmodi.rssreader.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.arkinmodi.rssreader.document.atom.AtomAuthor;
import com.arkinmodi.rssreader.document.atom.AtomEntryDocument;
import com.arkinmodi.rssreader.document.atom.AtomFeedDocument;
import java.io.IOException;
import java.io.StringReader;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AtomHandlerTests {

  @Test
  public void testParseCompleteAtomFeed()
      throws IOException, ParserConfigurationException, SAXException {
    String atomFeed =
        """
<?xml version="1.0" encoding="UTF-8"?>
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/" xml:lang="en-US">
  <id>atom-feed-id</id>
  <link type="text/html" rel="atom-feed-link-rel" href="atom-feed-link-href"/>
  <title>atom-feed-title</title>
  <updated>2023-09-16T01:25:28Z</updated>
  <author>
    <name>atom-feed-author-name</name>
    <uri>atom-feed-author-uri</uri>
    <email>atom-feed-author-email</email>
  </author>
  <entry>
    <id>atom-entry-id</id>
    <link type="text/html" rel="atom-entry-link-rel" href="atom-entry-link-href"/>
    <title>
      atom-entry-title
    </title>
    <updated>2023-09-15T01:25:28Z</updated>
    <published>2023-09-15T01:25:28Z</published>
    <author>
      <name>atom-entry-author-name</name>
      <uri>atom-entry-author-uri</uri>
      <email>atom-entry-author-email</email>
    </author>
    <content type="atom-entry-content-type">atom-entry-content</content>
    <summary>atom-entry-summary</summary>
  </entry>
</feed>
      """;

    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
    AtomHandler handler = new AtomHandler();
    InputSource inputSource = new InputSource(new StringReader(atomFeed));
    saxParser.parse(inputSource, handler);

    AtomFeedDocument actual = handler.getFeed();

    assertEquals("atom-feed-id", actual.getId());
    assertEquals(Map.of("atom-feed-link-rel", "atom-feed-link-href"), actual.getLinks());
    assertEquals("atom-feed-title", actual.getTitle());
    assertEquals(
        Optional.of(ZonedDateTime.of(2023, 9, 16, 1, 25, 28, 0, ZoneOffset.UTC)),
        actual.getUpdated());
    assertEquals(1, actual.getEntries().size());
    assertTrue(actual.getAuthor().isPresent());

    AtomEntryDocument actualEntry = actual.getEntries().get(0);

    assertEquals("atom-entry-id", actualEntry.getId());
    assertEquals(Map.of("atom-entry-link-rel", "atom-entry-link-href"), actualEntry.getLinks());
    assertEquals("atom-entry-title", actualEntry.getTitle());
    assertEquals(
        Optional.of(ZonedDateTime.of(2023, 9, 15, 1, 25, 28, 0, ZoneOffset.UTC)),
        actualEntry.getUpdated());
    assertEquals(
        Optional.of(ZonedDateTime.of(2023, 9, 15, 1, 25, 28, 0, ZoneOffset.UTC)),
        actualEntry.getPublished());
    assertEquals(Optional.of("atom-entry-content"), actualEntry.getContent());
    assertEquals(Optional.of("atom-entry-content-type"), actualEntry.getContentType());
    assertEquals(Optional.of("atom-entry-summary"), actualEntry.getSummary());
    assertTrue(actualEntry.getAuthor().isPresent());

    AtomAuthor actualAuthor = actual.getAuthor().get();
    assertEquals("atom-feed-author-name", actualAuthor.getName());
    assertEquals(Optional.of("atom-feed-author-email"), actualAuthor.getEmail());
    assertEquals(Optional.of("atom-feed-author-uri"), actualAuthor.getUri());

    AtomAuthor actualEntryAuthor = actualEntry.getAuthor().get();
    assertEquals("atom-entry-author-name", actualEntryAuthor.getName());
    assertEquals(Optional.of("atom-entry-author-email"), actualEntryAuthor.getEmail());
    assertEquals(Optional.of("atom-entry-author-uri"), actualEntryAuthor.getUri());
  }

  @Test
  public void testAtomFeedLinkMissingAttributes()
      throws IOException, ParserConfigurationException, SAXException {
    String atomFeed =
        """
<?xml version="1.0" encoding="UTF-8"?>
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/" xml:lang="en-US">
  <link />
</feed>
      """;

    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
    AtomHandler handler = new AtomHandler();
    InputSource inputSource = new InputSource(new StringReader(atomFeed));
    saxParser.parse(inputSource, handler);

    AtomFeedDocument actual = handler.getFeed();
    assertEquals(Collections.emptyMap(), actual.getLinks());
  }

  @Test
  public void testAtomEntryLinkMissingAttributes()
      throws IOException, ParserConfigurationException, SAXException {
    String atomFeed =
        """
<?xml version="1.0" encoding="UTF-8"?>
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/" xml:lang="en-US">
  <entry>
    <link />
  </entry>
</feed>
      """;

    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
    AtomHandler handler = new AtomHandler();
    InputSource inputSource = new InputSource(new StringReader(atomFeed));
    saxParser.parse(inputSource, handler);

    AtomEntryDocument actual = handler.getFeed().getEntries().get(0);
    assertEquals(Collections.emptyMap(), actual.getLinks());
  }

  @Test
  public void testAtomEntryContentTypeMissing()
      throws IOException, ParserConfigurationException, SAXException {
    String atomFeed =
        """
<?xml version="1.0" encoding="UTF-8"?>
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/" xml:lang="en-US">
  <entry>
    <content></content>
  </entry>
</feed>
      """;

    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
    AtomHandler handler = new AtomHandler();
    InputSource inputSource = new InputSource(new StringReader(atomFeed));
    saxParser.parse(inputSource, handler);

    AtomEntryDocument actual = handler.getFeed().getEntries().get(0);
    assertTrue(actual.getContentType().isEmpty());
  }
}
