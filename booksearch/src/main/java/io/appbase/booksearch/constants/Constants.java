package io.appbase.booksearch.constants;

public final class Constants {
  public static final String URL = "https://scalr.api.appbase.io";
  public static final String APP_NAME = "good-books-yj";
  public static final String TYPE = "good-books-ds";
  public static final String USERNAME = "gBgUqs2tV";
  public static final String PASSWORD = "3456f3bf-ea9e-4ebc-9c93-08eb13e5c87c";
  public static final String MATCH_ALL = "{ \"query\": { \"match_all\": {} } }";
  public static final String SEARCH =
      "{\n" +
      "  \"query\": {\n" +
      "    \"bool\": {\n" +
      "      \"must\": [\n" +
      "        {\n" +
      "          \"bool\": {\n" +
      "            \"must\": {\n" +
      "              \"bool\": {\n" +
      "                \"should\": [\n" +
      "                  {\n" +
      "                    \"multi_match\": {\n" +
      "                      \"query\": \"%s\",\n" +
      "                      \"fields\": [\n" +
      "                        \"original_title^3\",\n" +
      "                        \"original_title.raw^3\",\n" +
      "                        \"original_title.search^1\",\n" +
      "                        \"authors^2\",\n" +
      "                        \"authors.raw^2\",\n" +
      "                        \"authors.search^1\"\n" +
      "                      ],\n" +
      "                      \"type\": \"cross_fields\",\n" +
      "                      \"operator\": \"and\"\n" +
      "                    }\n" +
      "                  },\n" +
      "                  {\n" +
      "                    \"multi_match\": {\n" +
      "                      \"query\": \"%s\",\n" +
      "                      \"fields\": [\n" +
      "                        \"original_title^3\",\n" +
      "                        \"original_title.raw^3\",\n" +
      "                        \"original_title.search^1\",\n" +
      "                        \"authors^2\",\n" +
      "                        \"authors.raw^2\",\n" +
      "                        \"authors.search^1\"\n" +
      "                      ],\n" +
      "                      \"type\": \"phrase_prefix\",\n" +
      "                      \"operator\": \"and\"\n" +
      "                    }\n" +
      "                  }\n" +
      "                ],\n" +
      "                \"minimum_should_match\": \"1\"\n" +
      "              }\n" +
      "            }\n" +
      "          }\n" +
      "        }\n" +
      "      ]\n" +
      "    }\n" +
      "  }\n" +
      "}";

  private Constants() {
    throw new AssertionError("no instances");
  }
}
