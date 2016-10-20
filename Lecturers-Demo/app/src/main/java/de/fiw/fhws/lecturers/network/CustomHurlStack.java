package de.fiw.fhws.lecturers.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

class CustomHurlStack implements HttpStack {

	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private final UrlRewriter mUrlRewriter;
	private final SSLSocketFactory mSslSocketFactory;

	interface UrlRewriter {
		String rewriteUrl(String originalUrl);
	}

	CustomHurlStack() {
		this(null);
	}

	private CustomHurlStack(UrlRewriter urlRewriter) {
		this(urlRewriter, null);
	}

	private CustomHurlStack(UrlRewriter urlRewriter, SSLSocketFactory sslSocketFactory) {
		mUrlRewriter = urlRewriter;
		mSslSocketFactory = sslSocketFactory;
	}

	@Override
	public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
			throws IOException, AuthFailureError {
		String url = request.getUrl();
		HashMap<String, String> map = new HashMap<>();
		map.putAll(request.getHeaders());
		map.putAll(additionalHeaders);
		if (mUrlRewriter != null) {
			String rewritten = mUrlRewriter.rewriteUrl(url);
			if (rewritten == null) {
				throw new IOException("URL blocked by rewriter: " + url);
			}
			url = rewritten;
		}
		URL parsedUrl = new URL(url);
		HttpURLConnection connection = openConnection(parsedUrl, request);
		for (String headerName : map.keySet()) {
			connection.addRequestProperty(headerName, map.get(headerName));
		}
		setConnectionParametersForRequest(connection, request);
		ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
		int responseCode = connection.getResponseCode();
		if (responseCode == -1) {
			throw new IOException("Could not retrieve response code from HttpUrlConnection.");
		}
		StatusLine responseStatus = new BasicStatusLine(protocolVersion,
				connection.getResponseCode(), connection.getResponseMessage());
		BasicHttpResponse response = new BasicHttpResponse(responseStatus);
		if (hasResponseBody(request.getMethod(), responseStatus.getStatusCode())) {
			response.setEntity(entityFromConnection(connection));
		}
       //overwritten set header function
		for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
			if (header.getKey() != null) {
				String value = "";
				for (String head : header.getValue()) {
					value += head + ", ";
				}
				if (value.length() > 0)
					value = value.substring(0, value.length() - 2);

				Header h = new BasicHeader(header.getKey(), value);
				response.addHeader(h);
			}
		}
		return response;
	}

	private static boolean hasResponseBody(int requestMethod, int responseCode) {
		return requestMethod != Request.Method.HEAD
				&& !(HttpStatus.SC_CONTINUE <= responseCode && responseCode < HttpStatus.SC_OK)
				&& responseCode != HttpStatus.SC_NO_CONTENT
				&& responseCode != HttpStatus.SC_NOT_MODIFIED;
	}

	private static HttpEntity entityFromConnection(HttpURLConnection connection) {
		BasicHttpEntity entity = new BasicHttpEntity();
		InputStream inputStream;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException ioe) {
			inputStream = connection.getErrorStream();
		}
		entity.setContent(inputStream);
		entity.setContentLength(connection.getContentLength());
		entity.setContentEncoding(connection.getContentEncoding());
		entity.setContentType(connection.getContentType());
		return entity;
	}


	private HttpURLConnection createConnection(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());

		return connection;
	}

	private HttpURLConnection openConnection(URL url, Request<?> request) throws IOException {
		HttpURLConnection connection = createConnection(url);

		int timeoutMs = request.getTimeoutMs();
		connection.setConnectTimeout(timeoutMs);
		connection.setReadTimeout(timeoutMs);
		connection.setUseCaches(false);
		connection.setDoInput(true);

		if ("https".equals(url.getProtocol()) && mSslSocketFactory != null) {
			((HttpsURLConnection)connection).setSSLSocketFactory(mSslSocketFactory);
		}

		return connection;
	}

	@SuppressWarnings("deprecation")
    private static void setConnectionParametersForRequest(HttpURLConnection connection,
																Request<?> request) throws IOException, AuthFailureError {
		switch (request.getMethod()) {
			case Request.Method.DEPRECATED_GET_OR_POST:
				byte[] postBody = request.getPostBody();
				if (postBody != null) {
					connection.setDoOutput(true);
					connection.setRequestMethod("POST");
					connection.addRequestProperty(HEADER_CONTENT_TYPE,
							request.getPostBodyContentType());
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.write(postBody);
					out.close();
				}
				break;
			case Request.Method.GET:
				connection.setRequestMethod("GET");
				break;
			case Request.Method.DELETE:
				connection.setRequestMethod("DELETE");
				break;
			case Request.Method.POST:
				connection.setRequestMethod("POST");
				addBodyIfExists(connection, request);
				break;
			case Request.Method.PUT:
				connection.setRequestMethod("PUT");
				addBodyIfExists(connection, request);
				break;
			case Request.Method.HEAD:
				connection.setRequestMethod("HEAD");
				break;
			case Request.Method.OPTIONS:
				connection.setRequestMethod("OPTIONS");
				break;
			case Request.Method.TRACE:
				connection.setRequestMethod("TRACE");
				break;
			case Request.Method.PATCH:
				connection.setRequestMethod("PATCH");
				addBodyIfExists(connection, request);
				break;
			default:
				throw new IllegalStateException("Unknown method type.");
		}
	}

	private static void addBodyIfExists(HttpURLConnection connection, Request<?> request)
			throws IOException, AuthFailureError {
		byte[] body = request.getBody();
		if (body != null) {
			connection.setDoOutput(true);
			connection.addRequestProperty(HEADER_CONTENT_TYPE, request.getBodyContentType());
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(body);
			out.close();
		}
	}
}