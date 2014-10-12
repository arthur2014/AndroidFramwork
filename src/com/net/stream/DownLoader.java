package com.net.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


public interface DownLoader {

	public InputStream  getStream(String imageUri) throws IOException;

	public enum Scheme {
		HTTP("http"), HTTPS("https"), FILE("file"), CONTENT("content"), ASSETS(
				"assets"), DRAWABLE("drawable"), UNKNOWN("");

		private String scheme;
		private String uriPrefix;

		Scheme(String scheme) {
			this.scheme = scheme;
			uriPrefix = scheme + "://";
		}
		
		public static Scheme ofUri(String uri) {
			if (uri != null) {
				for (Scheme s : values()) {
					if (s.belongsTo(uri)) {
						return s;
					}
				}
			}
			return UNKNOWN;
		}
		
		private boolean belongsTo(String uri) {
			return uri.toLowerCase(Locale.US).startsWith(uriPrefix);
		}
		
		/** Removed scheme part ("scheme://") from incoming URI */
		public String crop(String uri) {
			if (!belongsTo(uri)) {
				throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", uri, scheme));
			}
			return uri.substring(uriPrefix.length());
		}
	}
}
