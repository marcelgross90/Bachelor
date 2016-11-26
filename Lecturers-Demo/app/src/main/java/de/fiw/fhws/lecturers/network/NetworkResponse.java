package de.fiw.fhws.lecturers.network;


import java.io.Reader;
import java.util.List;
import java.util.Map;

import de.fiw.fhws.lecturers.network.util.HeaderParser;
import de.marcelgross.lecturer_lib.model.Link;

public class NetworkResponse {

    private Reader responseReader;
    private Map<String,List<String>> header;

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
