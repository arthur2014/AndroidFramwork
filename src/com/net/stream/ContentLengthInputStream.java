package com.net.stream;

import java.io.IOException;
import java.io.InputStream;

public class ContentLengthInputStream extends InputStream{

	private InputStream inputStream;
	private int length;
	
	public ContentLengthInputStream(InputStream stream,int len) {
		this.inputStream=stream;
		this.length=len;
	}
	
	public int getLength(){
		return length;
	}
	@Override
	public int read() throws IOException {
		return inputStream.read();
	}
	
	@Override
	public int available() throws IOException {
		return length;
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}

	@Override
	public void mark(int readlimit) {
		inputStream.mark(readlimit);
	}

	@Override
	public boolean markSupported() {
		return inputStream.markSupported();
	}

	@Override
	public int read(byte[] buffer) throws IOException {
		return inputStream.read(buffer);
	}

	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException {
		return inputStream.read(buffer, offset, length);
	}

	@Override
	public synchronized void reset() throws IOException {
		inputStream.reset();
	}
	
	
}