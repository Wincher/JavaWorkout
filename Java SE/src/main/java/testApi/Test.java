//package testApi;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;​
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//​
///**
// * @since 2019-09-20
// * <p> testApi <p>
// */
//public class TestReferrence {
//​
//  public static void main(String[] args) throws InterruptedException {
//​
//    for (int i = 0; i < 3 ; i++) {
//      doSth();
//      TimeUnit.SECONDS.sleep(10);
//    }
//  }
//​
//  private static void doSth() throws InterruptedException {
////        ArrayList ssnArray = new ArrayList();
////        ssnArray.add( "8602276472");
////        ssnArray.add( "7005192112");
////        ssnArray.add( "7709107473");
////        ssnArray.add( "2604216990");
////        ssnArray.add( "8106277331");
//    ArrayList ssnArray = getSsnList();
//    final CountDownLatch endGate = new CountDownLatch(ssnArray.size());
//    final CountDownLatch startGate = new CountDownLatch(1);
//    for(int i = 0; i < ssnArray.size(); i++) {
//      final String ssn = ssnArray.get(i).toString();
//      new Thread(() -> {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://ws33.leandev.cn:8080/backend/CustomerAuthenticationServiceBean");
//        StringEntity data = new TestReferrence().getData(ssn, new Random().nextBoolean() ? "super" : "superit", "greenback");
//        httpPost.setEntity(data);
//        try {
//          CloseableHttpResponse response = client.execute(httpPost);
//          HttpEntity httpEntity = response.getEntity();
//          if (null != httpEntity) {
////            if( ! EntityUtils.toString(httpEntity).contains( "crs_url=" ) ){
////                System.out.println(ssn + " is valid crs: " + EntityUtils.toString(httpEntity));
////                response.close();
////                client.close();
////            }
////
////            if( !EntityUtils.toString(httpEntity).split("crs_url=")[1].contains("</") ){
////                System.out.println(ssn + " is valid </: " + EntityUtils.toString(httpEntity).split("crs_url=")[1] );
////                response.close();
////                client.close();
////            }
//            String a = EntityUtils.toString(httpEntity);
//            // System.out.println(a);
//            String responseStr = a.split("crs_url=")[1].split("</")[0].replace("&amp;", "&");
//            System.out.println(ssn + " : " + responseStr);
//            startGate.await();
//            new TestReferrence().loginMypage(ssn, responseStr);
//          }
//          response.close();
//          client.close();
//        }catch (Exception e ){
//          e.printStackTrace();
//        } finally {
//          endGate.countDown();
//        }
//      }, "Thead: " + i).start();
//    }
//    long startTime = System.nanoTime();
//    System.out.println(startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
//    startGate.countDown();
//    endGate.await();
//    long endTime = System.nanoTime();
//    System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
//  }
//  ​
//  private StringEntity getData(String ssn,String username, String password) {
//    return new StringEntity( "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:log=\"http://login.services.backend.greenback/\">\n" +
//        "   <soapenv:Header/>\n" +
//        "   <soapenv:Body>\n" +
//        "      <log:fetchUrlForLoginMypages>\n" +
//        "         <!--Optional:-->\n" +
//        "         <ssn>"+ssn+"</ssn>\n" +
//        "         <!--Optional:-->\n" +
//        "         <user>\n" +
//        "            <!--Optional:-->\n" +
//        "            <password>"+password+"</password>\n" +
//        "            <!--Optional:-->\n" +
//        "            <username>"+username+"</username>\n" +
//        "         </user>\n" +
//        "         <!--Optional:-->\n" +
//        "         <ip>10.10.6.147</ip>\n" +
//        "      </log:fetchUrlForLoginMypages>\n" +
//        "   </soapenv:Body>\n" +
//        "</soapenv:Envelope>", Charset.forName("UTF-8"));
//  }
//
//  public void loginMypage( String ssn ,String url){
//    long startTime = System.currentTimeMillis();
//    CloseableHttpClient aDefault = HttpClients.createDefault();
//​
//    HttpClientContext context = HttpClientContext.create();
//    HttpGet httpGet = new HttpGet(url);
//    try {
//      CloseableHttpResponse response = aDefault.execute(httpGet, context);
//      long time = System.currentTimeMillis()-startTime;
//      System.out.println( ssn+" login mypage  : " + response.getStatusLine() + " time : " + time );
////            HttpEntity httpEntity = response.getEntity();//           CookieStore cookieStore = context.getCookieStore();
////            List<Cookie> cookies = cookieStore.getCookies();
////            System.err.println(cookies.size());
////            for (Cookie cookie:cookies) {
////                System.out.println(cookie.getName() + "::: " + cookie.getValue());
////            }
////            if (null != httpEntity){
////                String responseStr = EntityUtils.toString(httpEntity, "UTF-8");
////                System.out.println( responseStr );
////            }
//      response.close();
//      aDefault.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//
//  public static ArrayList getSsnList(){
//    ArrayList<String> ssnArray = new ArrayList();
//    String driver = "org.postgresql.Driver";
//    String url = "jdbc:postgresql://ws47:6123/greenback";
//    String user = "postgres";
//    String password = "postgres";
//    try {
//      //加载驱动程序
//      Class.forName(driver);
//      Connection con = DriverManager.getConnection(url,user,password);
//      if(!con.isClosed())
//        System.out.println("Succeeded connecting to the Database!");
//      Statement statement = con.createStatement();
//      String sql = "select ssn from ( select ssn from depositaccount d, customer c where (d.accountholder_customerid=c.customerid or d.guardian1_customerid=c.customerid or d.guardian2_customerid=c.customerid)\n" +
//          "group by ssn limit 30 ) ssnList where ssn not like '%*%'";
//      ResultSet rs = statement.executeQuery(sql);
//      while(rs.next()){
//        ssnArray.add( rs.getString(1) );
//      }
//      rs.close();
//      con.close();
//    } catch(ClassNotFoundException e) {
//      //数据库驱动类异常处理
//      System.out.println("Sorry,can`t find the Driver!");
//      e.printStackTrace();
//    } catch(SQLException e) {
//      //数据库连接失败异常处理
//      e.printStackTrace();
//    }catch (Exception e) {
//      // TODO: handle exception
//      e.printStackTrace();
//    }finally{
//      System.out.println("数据库数据成功获取！！");
//    }
//    System.out.println( ssnArray.size() );
//    return ssnArray;
//  }​
//}