package com.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

import com.base.BaseConfigure;

/**
 * 
 * @author Administrator
 *
 */
public class MD5NameGenerator implements NameGenerator{

	private final String TAG="MD5NameGenerator";
	private static final String HASH_ALGORITHM = "MD5";
	private static final int RADIX = 10 + 26; // 10 digits + 26 letters
	@Override
	public String generate(String url) {
		byte[] md5=getMD5(url.getBytes());
		BigInteger big=new BigInteger(md5).abs();
		return big.toString(RADIX);
	}
	
	private byte[] getMD5(byte[] data) {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(data);
			hash = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			if(BaseConfigure.DEBUG) Log.e(TAG, e.toString());
		}
		return hash;
	}
}