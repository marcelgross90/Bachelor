package de.marcelgross.lecturer_lib.generic.network;


import java.io.Reader;
import java.util.List;
import java.util.Map;

import de.marcelgross.lecturer_lib.generic.model.Link;
import de.marcelgross.lecturer_lib.generic.network.util.HeaderParser;

public class NetworkResponse {

    private final Reader responseReader;
    private final Map<String,List<String>> header;

    public NetworkResponse(Reader responseReader, Map<String, List<String>> header) {
        this.responseReader = responseReader;
        this.header = header;
    }

    public Reader getResponseReader() {
        return responseReader;
    }

    public Map<String, List<String>> getHeader() {
        return header;
    }

    public Map<String, Link> getLinkHeader() {
        return HeaderParser.getLinks(header.get("link"));
    }
}
