package com.aio.portable.swiss.structure.bean.serializer.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonSugar {

	public GsonSugar() {
		// TODO Auto-generated constructor stub
	}

	private static Gson gson = new Gson();
	
	public static <T> T json2T(String jsonStr, Type typeOfT) {
		return gson.fromJson(jsonStr, typeOfT);
	}
	
	public static <T> T json2T(String jsonStr, Class<T> clazz) {
		return gson.fromJson(jsonStr, clazz);
	}
	
	public static String obj2Json(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T deepCopy(Object obj, Type typeOfT) {
		return gson.fromJson(gson.toJson(obj), typeOfT);
	}
	
	public static <T> T deepCopy(Object obj, Class<T> clazz) {
		return gson.fromJson(gson.toJson(obj), clazz);
	}

	/**
	 * getJsonValue
	 *
	 * @param jsonStr
	 * @param key
	 * @return
	 */
//    public static String getJsonItemValue(String jsonStr, String key) {
//        return new JSONObject(jsonStr).getString(key);
//    }
	

	
	
	
//	
//
//	public <T> T xGetExtra(String key, Class<T> clazz) throws FileNotFoundException, InstantiationException, IllegalAccessException {
//		T t = clazz.getDeclaredConstructor().newInstance();
//		XStream xStream = new XStream(new DomDriver());
//		String fullPath = path + key + extXml;
//		File file = new File(fullPath);
//		if (file.exists()) {
//			FileInputStream in = new FileInputStream(fullPath);
//			xStream.fromXML(in, t);
//			return t;
//		} else
//			return null;
//	}
	
	
}
