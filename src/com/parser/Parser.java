package com.parser;

import org.json.JSONObject;

public interface Parser{
	public  <T extends Object> void parse(JSONObject object,T t);
	
}