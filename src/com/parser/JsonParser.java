package com.parser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser implements Parser {

	@Override
	public <T extends Object> void parse(JSONObject jsonObject, T object) {
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			JSONObject targetJsonObject = jsonObject;
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				AutoFill autoFill = field.getAnnotation(AutoFill.class);
				if (autoFill != null) {
					explainAutoFill();
				}
				if (targetJsonObject.has(fieldName)) {
					Class<?> fieldClazz = field.getType();
					if (fieldClazz.isArray()) {
						Class<?> componentClazz=fieldClazz.getComponentType();
						JSONArray jsonArray=targetJsonObject.getJSONArray(fieldName);
						if(jsonArray==null) throw new IllegalArgumentException();
						int length=jsonArray.length();
						Object arrayField=Array.newInstance(componentClazz, length);
						for(int i=0;i<length;i++){
							if(ParserHelper.isPrimitiveType(componentClazz)){
								String value=jsonArray.getString(i);
								Object v=ParserHelper.getValue(componentClazz, value);
								Array.set(arrayField, i, v);
							}else{
								JSONObject jsonObject2= jsonArray.getJSONObject(i);
								Object valueObject=componentClazz.newInstance();
								parse(jsonObject2, valueObject);
								Array.set(arrayField, i, valueObject);
							}
						}
						field.set(object, arrayField);
					}else if(ParserHelper.isPrimitiveType(fieldClazz)){// primitive type
						String value=targetJsonObject.getString(fieldName);
						if(value==null) throw new IllegalArgumentException();
						ParserHelper.fill(object,field,value);
					} else {
						JSONObject fieldJson=targetJsonObject.getJSONObject(fieldName);
						if(fieldJson==null) throw new IllegalArgumentException();
						Object fieldObject = fieldClazz.newInstance();
						parse(fieldJson, fieldObject);
						field.set(object, fieldObject);
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private void explainAutoFill(){
	}
}