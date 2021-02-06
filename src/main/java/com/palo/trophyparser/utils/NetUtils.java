package com.palo.trophyparser.utils;

import java.io.IOException;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import lombok.extern.java.Log;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Log
public class NetUtils {

  private static final int BACKOFF_SLEEP = 1000;

  public static JsonStructure executeJsonHttpCall(HttpUriRequest request)
      throws ParseException, IOException {
    return executeJsonHttpCall(request, 0);
  }

  public static JsonStructure executeJsonHttpCall(HttpUriRequest request,
      int exponentialBackoffRetries) throws ParseException, IOException {
    return executeJsonHttpCall(request, exponentialBackoffRetries, 60);
  }

  public static JsonStructure executeJsonHttpCall(HttpUriRequest request,
      int exponentialBackoffRetries, int timeoutSeconds)
      throws ParseException, IOException {
    CloseableHttpResponse response = null;
    for (int i = 0; i < (exponentialBackoffRetries + 1); i++) {
      if (i > 0) {
        try {
          Thread.sleep((int) Math.pow(2.0, i) * BACKOFF_SLEEP);
        } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      try {
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(
            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD)
                .setConnectionRequestTimeout(timeoutSeconds * 1000)
                .setConnectTimeout(timeoutSeconds * 1000).setSocketTimeout(timeoutSeconds * 1000)
                .build()).build();
        response = client.execute(request);
        HttpEntity entity = response.getEntity();
        if (null == entity) {
          return null;
        }
        String responseString = EntityUtils.toString(entity);
        JsonReader jsonReader = Json
            .createReader(new StringReader(responseString));
        JsonStructure struc = jsonReader.read();
        jsonReader.close();
        return struc;
      } catch (Exception e) {
        e.printStackTrace();
        if (i == exponentialBackoffRetries) {
          throw e;
        }
      } finally {
        if (null != response) {
          response.close();
        }

      }
    }
    return null;
  }
}
