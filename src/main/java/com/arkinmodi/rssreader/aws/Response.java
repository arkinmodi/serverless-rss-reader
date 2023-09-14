package com.arkinmodi.rssreader.aws;

import java.util.HashMap;
import java.util.Map;

public class Response {
  private Map<String, String> headers;
  private String body;
  private boolean isBase64Encoded;
  private int statusCode;

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public boolean isBase64Encoded() {
    return isBase64Encoded;
  }

  public int getStatusCode() {
    return statusCode;
  }

  private Response(ResponseBuilder builder) {
    this.body = builder.body;
    this.headers = builder.headers;
    this.isBase64Encoded = builder.isBase64Encoded;
    this.statusCode = builder.statusCode;
  }

  public static class ResponseBuilder {
    private Map<String, String> headers = new HashMap<>(5);
    private String body;
    private boolean isBase64Encoded;
    private int statusCode;

    public ResponseBuilder header(String name, String value) {
      headers.put(name, value);
      return this;
    }

    public ResponseBuilder body(String body) {
      this.body = body;
      return this;
    }

    public ResponseBuilder isBase64Encoded(boolean isBase64Encoded) {
      this.isBase64Encoded = isBase64Encoded;
      return this;
    }

    public ResponseBuilder statusCode(int statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Response build() {
      return new Response(this);
    }
  }
}
