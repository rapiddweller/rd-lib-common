/*
 * Copyright (C) 2004-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.common.io;

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Calls HTTP servers supporting individual user agent setting.<br><br>
 * Created: 20.05.2021 21:25:45
 * @since 2.14.0
 * @author Volker Bergmann
 */

public class HttpClient {
	
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; de-DE; rv:1.7.5) Gecko/20041122 Firefox/1.0";
    
	private String userAgent;
	private String cookie;
	
	public HttpClient() {
		setUserAgent(DEFAULT_USER_AGENT);
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public String getCookie() {
		return cookie;
	}
	
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
    public String getContentOf(String uri) throws IOException {
        return getContentOf(uri, SystemInfo.getFileEncoding());
    }

    public String getContentOf(String url, String encoding) throws IOException {
        Reader reader = getHttpReader(new URL(url), encoding);
        return IOUtil.readAndClose(reader);
    }

	public BufferedReader getHttpReader(URL url, String defaultEncoding) 
			throws IOException {
		try {
		    URLConnection connection = getConnection(url);
		    connection.connect();
		    String encoding = IOUtil.encoding(connection, defaultEncoding);
		    InputStream inputStream = connection.getInputStream();
		    return new BufferedReader(new InputStreamReader(inputStream, encoding));
		} catch (MalformedURLException e) {
		    throw ExceptionFactory.getInstance().illegalArgument("Malformed URL: " + url, e);
		}
	}
	
    private URLConnection getConnection(URL url) throws IOException {
        URLConnection connection = url.openConnection();
       	connection.setRequestProperty("User-Agent", userAgent);
       	if (cookie != null)
       		connection.setRequestProperty("Cookie", cookie);
        connection.connect();
        return connection;
    }
    
}
