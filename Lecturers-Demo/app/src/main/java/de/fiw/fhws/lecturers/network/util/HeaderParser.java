package de.fiw.fhws.lecturers.network.util;

import java.util.HashMap;
import java.util.List;

import de.marcelgross.lecturer_lib.model.Link;

public class HeaderParser {

	public static HashMap<String, String> getLinks(String linkHeader) {
		if (linkHeader == null) {
			return null;
		}
//todo
		String[] links = linkHeader.split(",\\s*");
		HashMap<String, String> linkMap = new HashMap<>();

		for (String currentLinkAndRelType : links) {
			String[] linkPair = getLinkPair(currentLinkAndRelType);

			if (linkPair != null) {
				linkMap.put(linkPair[0], linkPair[1]);
			}
		}

		return linkMap;
	}

	public static HashMap<String, Link> getLinks(List<String> linkHeader) {
		HashMap<String, Link> linkHashMap = new HashMap<>();
		for (String s : linkHeader) {
			Link link = getLink(s);
			linkHashMap.put(link.getRel(), link);
		}

		return  linkHashMap;
	}

	public static String getUrlWithoutQueryParams(String url) {
		if (url.contains("?")) {
			return url.split("\\?")[0];
		}
		return url;
	}


	private static String[] getLinkPair(String linkAndRelType) {
		String[] links = linkAndRelType.split(";\\s*");
		String relType = null;
		String link = null;

		for (String currentLink : links) {
			if (currentLink.contains("rel=")) {
				relType = currentLink.replace("rel=", "").replace("\"", "");
			} else if (currentLink.contains("<")) {
				link = currentLink.replace("<", "").replace(">", "");
			}
		}

		return relType == null || link == null ? null : new String[]{relType, link};
	}

	private static Link getLink(String linkString) {
		String[] links = linkString.split(";\\s*");
		String relType = "";
		String mediaType = "";
		String url = "";

		for (String linkPart : links) {
			if (linkPart.contains("rel=")) {
				relType = linkPart.replace("rel=", "").replace("\"", "");
			} else  if (linkPart.contains("type=")) {
				mediaType = linkPart.replace("type=", "").replace("\"", "");
			} else if (linkPart.contains("<")) {
				url = linkPart.replace("<", "").replace(">", "");
			}
		}

		return new Link(url, relType, mediaType);

	}

}
