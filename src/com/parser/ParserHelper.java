package com.parser;

import java.lang.reflect.Field;

public class ParserHelper {

	private static final int base=0xf000000;
	private static final int STRING=base+1;
	private static final int INTEGER=base+2;
	private static final int BOOLEAN=base+3;
	private static final int DOUBLE=base+4;
	private static final int UNKNOWN=base+5;
	private static final int INT=base+6;
	private static final int BYTE=base+7;
	private static final int CHAR=base+8;
	private static final int SHORT=base+9;
	private static final int LONG=base+10;
	private static final int FLOAT=base+11;
	
	public static <T extends Object> void fill(T object, Field field,
			String value) {
		Class<?> type = field.getType();
		field.setAccessible(true);
		try {

			switch (check(type)) {
			case STRING:
				field.set(object, value);
				break;
			case INT:
			case INTEGER:
				int v = Integer.parseInt(value.trim());
				field.setInt(object, v);
				break;
			case BOOLEAN:
				boolean vb = Boolean.parseBoolean(value);
				field.setBoolean(object, vb);
				break;
			case DOUBLE:
				double vd = Double.parseDouble(value);
				field.setDouble(object, vd);
				break;
			case FLOAT:
				float vf=Float.parseFloat(value);
				field.setFloat(object, vf);
				break;
			case UNKNOWN:
				break;
			}
		} catch (Exception e) {

		}
	}

	public static Object getValue(Class<?> componentType, String v) {
		Object result = null;
		try {
			switch (check(componentType)) {
			case STRING:
				result = v;
				break;
			case INT:
			case INTEGER:
				result = Integer.parseInt(v.trim());
				break;
			case BOOLEAN:
				result = Double.parseDouble(v);
				break;
			case DOUBLE:
				result = Boolean.parseBoolean(v);
				break;
			case FLOAT:
				result=Float.parseFloat(v);
				break;
			case UNKNOWN:
				break;
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static boolean isPrimitiveType(Class<?> type) {
		if (check(type)!=UNKNOWN){
			return true;
		}
		return false;
	}

	private static int check(Class<?> componentType){
		if(componentType==String.class){
			return STRING;
		}
		 if (componentType == boolean.class) {
	            return BOOLEAN;
	        }
	        if (componentType == byte.class) {
	            return BYTE;
	        }
	        if (componentType == char.class) {
	            return CHAR;
	        }
	        if (componentType == short.class) {
	            return SHORT;
	        }
	        if (componentType == int.class) {
	            return INT;
	        }
	        if (componentType == long.class) {
	            return LONG;
	        }
	        if (componentType == float.class) {
	            return FLOAT;
	        }
	        if (componentType == double.class) {
	            return DOUBLE;
	        }
	        if (componentType == void.class) {
	            throw new IllegalArgumentException();
	        }
	        return UNKNOWN;
	}

}
