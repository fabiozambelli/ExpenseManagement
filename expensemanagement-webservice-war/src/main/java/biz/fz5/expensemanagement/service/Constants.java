/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.service;

import org.apache.log4j.Logger;

/**
 * @author fabiozambelli
 *
 */
public class Constants {

	protected static Logger log = Logger.getLogger(Constants.class.getName());

	public static String getValue(String key) {
		ApplicationProperties applicationProperties;
		try {
			applicationProperties = new ApplicationProperties(
					"/application.properties");
			return applicationProperties.getProperty(key);
		} catch (Exception e) {
			log
					.fatal("Non riesco a trovare o leggere il file di properties necessario per continuare.");
			return null;
		}

	}
}
