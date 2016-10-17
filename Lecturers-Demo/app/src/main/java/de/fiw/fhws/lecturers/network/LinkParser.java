package de.fiw.fhws.lecturers.network;

/**
 * Created by marcelgross on 14.10.16.
 */

public class LinkParser {

	public static String gerateNewUrl(String url, int newSize, int newOffset) {
		return url
				.replace("size=" + getSize(url), "size=" + newSize)
				.replace("offset=" + getOffset(url), "offset=" + newOffset);
	}

	public static int getOffset(String url) {
		return extractUrl(url, "offset");
	}

	public static int getSize(String url) {
		return extractUrl(url, "size");
	}

	private static int extractUrl(String url, String param) {
		if (url.contains(param)){
			String params = url.split("\\?")[1];
			String[] singleParams = params.split("&");
			for(String currentParam : singleParams) {
				if (currentParam.contains(param + "=")) {
					return Integer.valueOf(currentParam.replace(param + "=", ""));
				}
			}

			return 0;
		} else {
			return 0;
		}
	}
}
