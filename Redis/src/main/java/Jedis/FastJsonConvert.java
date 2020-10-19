package Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @author wincher
 * @date   28/09/2017.
 */
public class FastJsonConvert {
	
	private static final SerializerFeature[] featuresWithNullValue = {
			SerializerFeature.WriteMapNullValue,
			SerializerFeature.WriteNonStringKeyAsString,
			SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.WriteNullNumberAsZero,
			SerializerFeature.WriteNullStringAsEmpty
	};
	
	/**
	 * JSONString to Object
	 *
	 * @param data
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T convertJSONToObject(String data, Class<T> clazz) {
			return JSON.parseObject(data, clazz);
	}
	
	/**
	 * JSONString to List<Object>
	 *
	 * @param data
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> conertJSONToArray(String data, Class<T> clazz) {
		return JSON.parseArray(data, clazz);
	}
	
	/**
	 * Object to JSONString
	 *
	 * @param obj
	 * @return
	 */
	public static String convetObjectToJson(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	/**
	 * todo: 待完成
	 *
	 * @param obj
	 * @return
	 */
	public static String convertObjectToJSONWithNullValue(Object obj) {
		return null;
	}
}
