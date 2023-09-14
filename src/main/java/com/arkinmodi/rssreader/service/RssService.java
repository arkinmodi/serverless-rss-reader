package com.arkinmodi.rssreader.service;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RssService {
  public static enum RssTypes {
    ATOM,
    RSS1,
    RSS2,
  }

  public RssTypes getRssType(InputSource in)
      throws UnknownRssTypeException, ParserConfigurationException, SAXException, IOException {

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(in);
    Element root = doc.getDocumentElement();

    if ("rss".equalsIgnoreCase(root.getNodeName())) {
      if (root.getAttribute("version").startsWith("2")) {
        return RssTypes.RSS2;
      } else {
        return RssTypes.RSS1;
      }
    } else if ("feed".equalsIgnoreCase(root.getNodeName())) {
      return RssTypes.ATOM;
    }
    throw new UnknownRssTypeException("Unrecognized RSS Type");
  }

  class UnknownRssTypeException extends Exception {
    public UnknownRssTypeException(String errorMessage) {
      super(errorMessage);
    }
  }
}
