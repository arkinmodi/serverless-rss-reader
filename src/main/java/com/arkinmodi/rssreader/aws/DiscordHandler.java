package com.arkinmodi.rssreader.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.arkinmodi.rssreader.aws.Response.ResponseBuilder;

public class DiscordHandler implements RequestHandler<Event, Response> {

  @Override
  public Response handleRequest(Event event, Context context) {
    return new ResponseBuilder()
        .statusCode(503)
        .body("This is a placeholder lambda function handler. Please upload the real application.")
        .build();
  }
}
