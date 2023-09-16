package com.arkinmodi.rssreader.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.arkinmodi.rssreader.aws.Response.ResponseBuilder;
import com.arkinmodi.rssreader.discord.InteractionVerify;
import com.arkinmodi.rssreader.discord.InteractionVerify.BadSignatureException;
import com.arkinmodi.rssreader.discord.command.ApplicationCommand;
import com.arkinmodi.rssreader.discord.command.ApplicationCommandTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Base64;
import java.util.Map;

public class DiscordHandler implements RequestHandler<Event, Response> {

  private final Gson gson;
  private final InteractionVerify interactionVerify;

  public DiscordHandler() {
    this.gson = new GsonBuilder().create();
    this.interactionVerify = new InteractionVerify(System.getenv("DISCORD_BOT_PUBLIC_KEY"));
  }

  @Override
  public Response handleRequest(Event event, Context context) {
    String requestBodyString = "";
    if (event.isBase64Encoded()) {
      requestBodyString = new String(Base64.getDecoder().decode(event.getBody()));
    } else {
      requestBodyString = event.getBody();
    }

    System.out.println("BODY: " + requestBodyString);
    System.out.println("HEADER: " + event.getHeaders());
    System.out.println("ENCODED: " + event.isBase64Encoded());

    try {
      interactionVerify.verify(event.getHeaders(), event.getBody());
    } catch (BadSignatureException e) {
      return new ResponseBuilder().statusCode(401).body(e.getMessage()).build();
    }

    ApplicationCommand requestBody = gson.fromJson(requestBodyString, ApplicationCommand.class);

    if (ApplicationCommandTypes.CHAT_INPUT.equals(requestBody.getType())) {
      return new ResponseBuilder()
          .statusCode(200)
          .header("content-type", "application/json")
          .body(gson.toJson(Map.of("type", 1)))
          .build();
    }

    return new ResponseBuilder()
        .statusCode(200)
        .header("content-type", "application/json")
        .body(gson.toJson(Map.of("type", 5)))
        .build();
  }
}
