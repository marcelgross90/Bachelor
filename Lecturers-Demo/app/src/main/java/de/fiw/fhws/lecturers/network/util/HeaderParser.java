package de.fiw.fhws.lecturers.network.util;

import java.util.HashMap;
import java.util.List;

import de.marcelgross.lecturer_lib.model.Link;

public class HeaderParser {

	public static HashMap<String, Link> getLinks(List<String> linkHeader) {
		HashMap<String, Link> linkHashMap = new HashMap<>();
		for (String s : linkHeader) {
			Link link = getLink(s);
			linkHashMap.put(link.getRel(), link);
		}

		return linkHashMap;
	}

	private static Link getLink(String linkString) {
		String[] links = linkString.split(";\\s*");
		String relType = "";
		String mediaType = "";
		String url = "";

		for (String linkPart : links) {
			if (linkPart.contains("rel=")) {
				relType = linkPart.replace("rel=", "").replace("\"", "");
			} else if (linkPart.contains("type=")) {
				mediaType = linkPart.replace("type=", "").replace("\"", "");
			} else if (linkPart.contains("<")) {
				url = linkPart.replace("<", "").replace(">", "");
			}
		}

		return new Link(url, relType, mediaType);
	}
}