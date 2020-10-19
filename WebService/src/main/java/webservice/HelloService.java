package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

/**
 * @author wincher
 * @date 2018/10/18
 * <p> webservice <p>
 */
//@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebService
public class HelloService {

  @WebMethod
  public String sayHi(String name) {
    return "Hi, " + name;
  }
  
  public static void main(String[] args) {
    Endpoint endpoint = Endpoint.publish("http://localhost:8080/hiws", new HelloService());
  }
}
