package testApi;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @date 2019-09-20
 * <p> testApi <p>
 */
public class RequestLoginToMypages implements Runnable {
  
  public static void main(String[] args) {
    for (int i = 0; i < 100 ; i++) {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        new Thread(new RequestLoginToMypages()).start();
      }
    }
  }
  
  @Override
  public void run() {
    CloseableHttpClient aDefault = HttpClients.createDefault();
  
    HttpClientContext context = HttpClientContext.create();
    HttpPost httpPost = new HttpPost("http://domain/someservice");
    StringEntity data = getData(new Random().nextBoolean()?"a":"b", "123");
    httpPost.setEntity(data);
    try {
      CloseableHttpResponse response = aDefault.execute(httpPost, context);
      HttpEntity httpEntity = response.getEntity();
      CookieStore cookieStore = context.getCookieStore();
      List<Cookie> cookies = cookieStore.getCookies();
      for (Cookie cookie:cookies) {
        System.out.println(cookie.getName() + ": " + cookie.getValue());
      }
      if (null != httpEntity){
        String responseStr = EntityUtils.toString(httpEntity, "UTF-8");
        System.out.println(responseStr);
      }
      response.close();
      aDefault.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private StringEntity getData(String username, String password) {
    return new StringEntity( "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:log=\"http:///\">\n", Charset.forName("UTF-8"));
  }
}
