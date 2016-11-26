package de.fiw.fhws.lecturers.util;


import com.owlike.genson.Genson;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class GensonBuilder {

	public Genson getDateFormatter() {
		return new com.owlike.genson.GensonBuilder()
				.useDateAsTimestamp(false)
				.useDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMANY))
				.create();
	}
}
