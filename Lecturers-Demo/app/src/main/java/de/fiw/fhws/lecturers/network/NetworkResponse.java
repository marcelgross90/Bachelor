package de.fiw.fhws.lecturers.network;


import java.io.Reader;
import java.util.Map;

import de.marcelgross.lecturer_lib.model.Link;

public class NetworkResponse {

    private Reader responseReader;
    private Map<String,Link> linkHeader;

    public NetworkResponse(Reader responseReader, Map<String, Link> linkHeader) {
        this.responseReader = responseReader;
        this.linkHeader = linkHeader;
    }

    public Reader getResponseReader() {
        return responseReader;
    }

    public Map<String, Link> getLinkHeader() {
        return linkHeader;
    }
}
