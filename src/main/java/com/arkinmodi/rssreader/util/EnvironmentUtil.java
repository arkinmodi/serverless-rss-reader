package com.arkinmodi.rssreader.util;

public class EnvironmentUtil {

  public String getEnv(String env) {
    String value = System.getenv(env);
    if (value == null) {
      throw new MissingEnvironmentVariableException(env);
    }
    return value;
  }

  public class MissingEnvironmentVariableException extends RuntimeException {
    public MissingEnvironmentVariableException(String env) {
      super("Missing Environment Variable: %s".formatted(env));
    }
  }
}
