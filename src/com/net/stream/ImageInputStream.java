package com.net.stream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.util.IoUtil;

public class ImageInputStream implements DownLoader{

	public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000; // milliseconds
	public static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000; // milliseconds
	
	protected static final int MAX_REDIRECT_COUNT = 5;
	protected static final int BUFFER_SIZE = 32 * 1024; // 32 Kb
	
	protected static final String CONTENT_CONTACTS_URI_PREFIX = "content://com.android.contacts/";
	private static final String ERROR_UNSUPPORTED_SCHEME = "UIL doesn't support scheme(protocol) by default [%s]. " + "You should implement this support yourself (BaseImageDownloader.getStreamFromOtherSource(...))";
	
	private static int connectTimeout;
	private static int readTimeout;
	private static Context context;
	public ImageInputStream(Context con){
		context=con;
		connectTimeout=DEFAULT_HTTP_CONNECT_TIMEOUT;
		readTimeout=DEFAULT_HTTP_READ_TIMEOUT;
	}
	
	public ImageInputStream(){
		this(null);
	}
	
	public  InputStream getStream(String url) throws IOException{
	if(url==null) throw new NullPointerException();
		switch (Scheme.ofUri(url)) {
		case HTTP:
		case HTTPS:
			return getFromNetWork(url);
		case FILE:
			return getFromFile(url);
		case ASSETS:
			return getFromAsset(url);
		case CONTENT :
			return getFromContent(url);
		case  DRAWABLE:
			return getFromDrawble(url);
		case UNKNOWN:
		default:
			return getFromOtherSource(url);
		}
	}
	
	private static InputStream getFromNetWork(String url)throws IOException{
		HttpURLConnection conn =createConnection(url);
		int redirectCount = 0;
		while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
			conn = createConnection(conn.getHeaderField("Location"));
			redirectCount++;
		}
		InputStream imageStream;
		try {
			imageStream = conn.getInputStream();
		} catch (IOException e) {
			// Read all data to allow reuse connection (http://bit.ly/1ad35PY)
			IoUtil.readAndCloseStream(conn.getErrorStream());
			throw e;
		}
		return new ContentLengthInputStream(new BufferedInputStream(imageStream,BUFFER_SIZE),conn.getContentLength());
		
	}
	private static HttpURLConnection createConnection(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		return conn;
	}
	
	private static InputStream getFromFile(String url) throws IOException{
		String path=Scheme.FILE.crop(url);
		File file=new File(path);
		FileInputStream fi=new FileInputStream(file);
		return new ContentLengthInputStream(new BufferedInputStream(fi,BUFFER_SIZE),(int)file.length());
	}
	
	private static InputStream getFromContent(String url) throws IOException{
		ContentResolver resolver=context.getContentResolver();
		Uri uri=Uri.parse(url);
		String mimeType=resolver.getType(uri);
		if(mimeType.startsWith("video/")){
			long origId=Long.valueOf(uri.getLastPathSegment());
			Bitmap bitmap=MediaStore.Video.Thumbnails.getThumbnail(resolver, origId, MediaStore.Images.Thumbnails.MINI_KIND, null);
			if(bitmap!=null){
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 0, bos);
				return new ByteArrayInputStream(bos.toByteArray());
			}
		}else if(url.startsWith(CONTENT_CONTACTS_URI_PREFIX)){
			ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
		}
		return resolver.openInputStream(uri);
	}
	
	private static InputStream getFromAsset(String url) throws IOException{
		String filePath = Scheme.ASSETS.crop(url);
		return context.getAssets().open(filePath);
	}
	
	private static InputStream getFromDrawble(String url){
		String drawableIdString = Scheme.DRAWABLE.crop(url);
		int drawableId = Integer.parseInt(drawableIdString);
		return context.getResources().openRawResource(drawableId);
	}
	private static InputStream getFromOtherSource(String url){
		throw new UnsupportedOperationException(String.format(ERROR_UNSUPPORTED_SCHEME, url));
	}
}
