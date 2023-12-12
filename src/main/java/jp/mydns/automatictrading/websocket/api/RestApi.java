package jp.mydns.automatictrading.websocket.api;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jp.mydns.automatictrading.websocket.constant.API_CONST;

@Service
public class RestApi {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public String get(String token, String urlText, Object parameter) {
    try {
      HttpRequest httpRequest = null;
      if (token != null) {
        httpRequest = HttpRequest.newBuilder(URI.create(urlText + getParameter(parameter)))
            .header("Content-Type", API_CONST.CONTENT_TYPE)
            .header("X-API-KEY", token)
            .GET().build();
      } else {
        httpRequest = HttpRequest.newBuilder(URI.create(urlText + getParameter(parameter)))
            .header("Content-Type", API_CONST.CONTENT_TYPE)
            .GET().build();
      }

      HttpClient httpClient = HttpClient.newHttpClient();

      HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      // 結果は呼び出し元に返しておく
      return httpResponse.body();
    } catch (Exception e) {
      logger.error("システムエラーが発生しました。", e);
    }
    return "";
  }

  public String post(String urlText, String json) {
    return post(null, urlText, json);
  }

  public String post(String token, String urlText, String json) {
    try {
      HttpRequest httpRequest = null;
      if (token != null) {
        httpRequest = HttpRequest.newBuilder(URI.create(urlText))
            .header("Content-Type", API_CONST.CONTENT_TYPE)
            .header("X-API-KEY", token)
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

      } else {
        httpRequest = HttpRequest.newBuilder(URI.create(urlText))
            .header("Content-Type", API_CONST.CONTENT_TYPE)
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

      }
      HttpClient httpClient = HttpClient.newHttpClient();

      HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      // 結果は呼び出し元に返しておく
      return httpResponse.body();
    } catch (Exception e) {
      logger.error("システムエラーが発生しました。", e);
    }
    return json;
  }

  public String put(String token, String urlText, String json) {
    try {
      HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(urlText))
          .header("Content-Type", API_CONST.CONTENT_TYPE)
          .header("X-API-KEY", token)
          .PUT(HttpRequest.BodyPublishers.ofString(json))
          .build();

      HttpClient httpClient = HttpClient.newHttpClient();

      HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      // 結果は呼び出し元に返しておく
      return httpResponse.body();
    } catch (Exception e) {
      logger.error("システムエラーが発生しました。", e);
    }
    return json;
  }

  /**
   * @param parameter
   * @return
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  public String getParameter(Object parameter) throws IllegalArgumentException, IllegalAccessException {
    if (parameter == null) {
      return "";
    } else {
      Field[] fields = parameter.getClass().getDeclaredFields();
      StringBuilder sb = new StringBuilder("?");
      for (int i = 0; i < fields.length; i++) {
        fields[i].setAccessible(true);
        if (fields[i].get(parameter) != null) {
          if (i >= 1) {
            sb.append("&");
          }
          sb.append(fields[i].getName() + "=" + fields[i].get(parameter));
        }
      }
      return sb.toString();
    }
  }

}
