package EJB;

import javax.ejb.Stateless;
import java.math.BigDecimal;

/**
 * @author wincher
 * @date 2019-03-03
 * <p> EJB <p>
 */
@Stateless
public class ConverterBean {
  private final BigDecimal yenRate = new BigDecimal("83.00602");
  private final BigDecimal euroRate = new BigDecimal("0.0093016");
  
  public BigDecimal dollarToYen(BigDecimal dollars) {
    BigDecimal result = dollars.multiply(yenRate);
    return result.setScale(2, BigDecimal.ROUND_UP);
  }
  
  public BigDecimal yenToEuro(BigDecimal yen) {
    BigDecimal result = yen.multiply(euroRate);
    return result.setScale(2, BigDecimal.ROUND_UP);
  }
  
  
}
