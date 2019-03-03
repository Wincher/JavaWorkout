package EJB;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author wincher
 * @since 2019-03-03
 * <p> EJB <p>
 */
@WebServlet(urlPatterns = "/")
public class ConverterServlet extends HttpServlet {
  
  @EJB
  ConverterBean converter;
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String amount = req.getParameter("amount");
      if (Objects.nonNull(amount) && amount.length() > 0) {
        BigDecimal d = new BigDecimal(amount);
        BigDecimal yenAmount = converter.dollarToYen(d);
        BigDecimal euroAmount = converter.yenToEuro(yenAmount);
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write((yenAmount + " " + euroAmount).getBytes());
        outputStream.flush();
        outputStream.close();
      }
    } catch (Exception e) {
      throw new RuntimeException("");
    }
    super.doPost(req, resp);
  }
}
