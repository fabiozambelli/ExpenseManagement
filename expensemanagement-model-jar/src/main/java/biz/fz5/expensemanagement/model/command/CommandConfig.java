/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

/**
 * @author fabiozambelli
 *
 */
public class CommandConfig {

	/*
	public static final String DIR_PUB = "/home/ubuntu/ocr-demo/public/";
	public static final String DIR_TMP = "/home/ubuntu/ocr-demo/tmp/";
	public static final String DIR_OUT = "/home/ubuntu/ocr-demo/processed/";
	*/
	public static final String DIR_PUB = "/var/www/ocr-demo/public/";
	public static final String DIR_TMP = "/var/www/ocr-demo/tmp/";
	public static final String DIR_OUT = "/var/www/ocr-demo/processed/";

	public static String replace(String str, String pattern, String replace) {

		int s = 0;
		int e = 0;

		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}

		result.append(str.substring(s));

		return result.toString();
	}
}
