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
 * @since 2019-09-20
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
    HttpPost httpPost = new HttpPost("http://ws77.leandev.cn:8080/backend/CustomerAuthenticationServiceBean");
    StringEntity data = getData(new Random().nextBoolean()?"super":"superit", "greenback");
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
    return new StringEntity( "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:log=\"http://login.services.backend.greenback/\">\n" +
        "   <soapenv:Header/>\n" +
        "   <soapenv:Body>\n" +
        "      <log:fetchUrlForLoginMypages>\n" +
        "         <!--Optional:-->\n" +
        "         <ssn>" + "7509287897" +
        "</ssn>\n" +
        "         <!--Optional:-->\n" +
        "         <user>\n" +
        "            <!--Optional:-->\n" +
        "            <password>greenback</password>\n" +
        "            <!--Optional:-->\n" +
        "            <username>super</username>\n" +
        "         </user>\n" +
        "         <!--Optional:-->\n" +
        "         <ip>10.10.3.235</ip>\n" +
        "      </log:fetchUrlForLoginMypages>\n" +
        "   </soapenv:Body>\n" +
        "</soapenv:Envelope>", Charset.forName("UTF-8"));
  }
}
