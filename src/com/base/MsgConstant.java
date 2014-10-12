package com.base;

public class MsgConstant {

	private static int BaseMsg=0xf00000;
	/*
	 * data require
	 */
	public static int DataRequire=BaseMsg+1;
	public static int DataResponse=BaseMsg+2;
	public static int ShowDialog=BaseMsg+3;
	public static int CloseDialog=BaseMsg+4;
	public static int DataRequireFailed=BaseMsg+5;
	
	/*
	 * image cache
	 */
	public static int ImageChanged=BaseMsg+100;
	
}
