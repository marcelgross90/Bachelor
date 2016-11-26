package de.fiw.fhws.lecturers.network;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetworkRequest {

    private Request.Builder requestBuilder;

    public NetworkRequest() {
        this.requestBuilder = new Request.Builder();
    }

    public NetworkRequest url(String url) {
        this.requestBuilder.url(url);
        return this;
    }

    public NetworkRequest acceptHeader(String acceptHeader) {
        return addHeader("Accept", acceptHeader);
    }

    public NetworkRequest addHeader(String header, String value) {
        this.requestBuilder.addHeader(header, value);
        return this;
    }

    public NetworkRequest post(String payload, String mediaType) {
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), payload);
        this.requestBuilder.post(body);
        return this;
    }

    public NetworkRequest put(String payload, String mediaType) {
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), payload);
        this.requestBuilder.put(body);
        return this;
    }

    public NetworkRequest delete() {
        this.requestBuilder.delete();
        return this;
    }

    public Request buildRequest() {
        return this.requestBuilder.build();
    }
}
