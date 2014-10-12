package com.decode;

import java.io.File;
import java.io.IOException;

public interface Decoder<T>{
	
	/**
	 * Decodes File to {@link File} according target size and other parameters.
	 *
	 * @param DecodingInfo
	 * @return T
	 * @throws IOException
	 */
	T decode(DecodeInfo DecodingInfo) throws IOException;
}