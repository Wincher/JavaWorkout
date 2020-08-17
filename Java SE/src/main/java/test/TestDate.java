package test;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import se.leandev.msa.play.commons.restful.JsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
	
	@Test
	public void testDate() throws ParseException {
		Date date = new Date();
		Date parse = new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-2");
		System.out.println(date);
		JsonUtils jsonUtils = new JsonUtils();
		String jsonStr = "{\"id\":\"5ecffa98cd71816d2be99aa0\",\"collateralType\":\"CO_OP\",\"documents\":[],\"dataValuation\":null,\"mortgageCollateral\":{\"ltv\":0.9222,\"internalLTV\":0.0721,\"marketValue\":\"3529000\",\"mortgageType\":\"FIRSTTIMEBUYER\",\"downPayment\":\"300000\",\"accessDate\":\"2020-11-20\"},\"owners\":null,\"collateralVerified\":false,\"area\":\"40\",\"numberOfRooms\":null,\"createTime\":1590688408439,\"updateTime\":1590742052393,\"metadata\":{\"mortgageOriginationId\":\"5ecff94fcd71816d2be99a9f\",\"applicationId\":\"10021\"},\"address\":{\"city\":null,\"streetAddress\":null,\"zipCode\":null,\"county\":\"DANDERYD\"},\"ucObjectId\":null,\"skvLApartmentNumber\":\"\",\"monthlyFee\":\"5000\",\"brfinfo\":{\"organizationNumber\":\"\",\"associationName\":\"\",\"associationAddress\":\"\",\"associationCoAddress\":null,\"associationPostalCode\":\"\",\"city\":\"\",\"county\":null,\"sumOfLivingArea\":null,\"numberOfApartments\":null},\"brfekonomi\":{\"liabilities\":null,\"liabilitiesOrKvm\":null,\"annualYear\":null}}";
		JsonNode parse1 = jsonUtils.parse(jsonStr);
		jsonStr = "{\"accessDate\":1590865637110}";
		jsonStr = "{\"accessDate\":\"2020-11-20\"}";
		JsonNode parse2 = jsonUtils.parse(jsonStr);
		Ad ad2 = new Ad();
		ad2.setAccessDate(new Date());
		Ad ad = jsonUtils.fromJson(parse2, Ad.class);
		System.out.println();
	}
	
	
}
