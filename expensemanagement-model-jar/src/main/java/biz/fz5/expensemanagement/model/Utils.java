package biz.fz5.expensemanagement.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Utils {

	public static String getDataFormat(long timestamp, String pattern) {

		String dataFormat = null;

		java.sql.Date d = new Date(timestamp);
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);		
		dataFormat = formatter.format(d);
		
		return dataFormat;

	}
}
