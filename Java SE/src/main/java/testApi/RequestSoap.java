package testApi;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @since 2019-09-20
 * <p> testApi <p>
 */
public class RequestSoap implements Runnable {
  
  public static void main(String[] args) {
    for (int i = 0; i < 10 ; i++) {
      while (true) {
        try {
          TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        new Thread(new RequestSoap()).start();
      }
    }
  }
  
  @Override
  public void run() {
    CloseableHttpClient aDefault = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost("http://url/someservice");
    StringEntity data = getData(new Random().nextBoolean()?"b":"a", "123");
    httpPost.setEntity(data);
    try {
      CloseableHttpResponse response = aDefault.execute(httpPost);
      HttpEntity httpEntity = response.getEntity();
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
    return new StringEntity( "soap content", Charset.forName("UTF-8"));
  }
}
