package com.arkinmodi.rssreader;

import com.arkinmodi.rssreader.service.RssService;
import com.arkinmodi.rssreader.service.RssService.RssTypes;
import com.arkinmodi.rssreader.xmlhandler.AtomHandler;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

class Main {

  public static void main(String[] args) throws Exception {

    // NOTE: Sample Atom Feeds
    // https://www.youtube.com/feeds/videos.xml?channel_id=UCry1ZVKLslbZXuQgsf-3TXg
    // https://github.com/arkinmodi/serverless-rss-reader/commits/main.atom

    // NOTE: Sample RSS 2.0 Feeds
    // https://blog.boot.dev/index.xml

    String testUrl = "https://github.com/arkinmodi/serverless-rss-reader/commits/main.atom";

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(new URI(testUrl))
            .GET()
            .header("accept", "application/xml")
            .timeout(Duration.ofSeconds(60))
            .build();

    HttpResponse<String> response =
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    InputSource test = new InputSource(new StringReader(response.body()));
    RssService rssService = new RssService();
    RssTypes type = rssService.getRssType(test);
    test = new InputSource(new StringReader(response.body()));

    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    SAXParser saxParser = saxParserFactory.newSAXParser();

    if (type.equals(RssTypes.ATOM)) {
      AtomHandler handler = new AtomHandler();
      saxParser.parse(test, handler);

      System.out.println(handler.getFeed().getTitle());
      System.out.println(handler.getFeed().getUpdated());
      System.out.println(handler.getFeed().getLinks());

    } else if (type.equals(RssTypes.RSS2)) {
      // TODO: RSS 2.0
      System.out.println("Parse RSS2");
    } else {
      // TODO: RSS 1.0 if I feel like it
      System.out.println("Parse RSS1");
    }
  }
}
