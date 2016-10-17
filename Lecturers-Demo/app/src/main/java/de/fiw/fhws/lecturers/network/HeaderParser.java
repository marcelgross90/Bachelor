package de.fiw.fhws.lecturers.network;

import java.util.HashMap;

public class HeaderParser {

	public static HashMap<String, String> getLinks(String linkHeader) {
		if (linkHeader == null) {
			return null;
		}

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

}
