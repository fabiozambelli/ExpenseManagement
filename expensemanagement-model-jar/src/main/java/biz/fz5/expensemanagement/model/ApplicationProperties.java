/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author fabiozambelli
 *
 */
public class ApplicationProperties {

	private Properties p = null;

	protected static Logger log = Logger.getLogger(ApplicationProperties.class
			.getName());

	/**
	 * Metodo di lettura del file passato come parametro di input su cui viene
	 * creato l'obj di properties
	 * 
	 * @param fileName
	 *            file su cui vengono salvati i parametri di configurazione
	 * @throws Exception
	 */
	public ApplicationProperties(String fileName) throws Exception {

		// read file
		File file = new File(fileName);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			is = getClass().getResourceAsStream(fileName);
			if (is == null) {
				log.fatal("Property file not found! File.exists() = "
						+ file.exists(), e);
				throw new Exception(e);
			}
		}

		// read data
		p = new Properties();
		try {
			p.load(is);
		} catch (Exception e) {
			log.fatal("Can't read the properties file. " + "Make sure " + file
					+ " is in the CLASSPATH\n" + e.toString(), e);
			throw new Exception(e);
		} finally {
			if (is != null)
				is.close();
		}
	}

	/**
	 * Metodo che riporta l'obj Properties
	 * 
	 * @return un obj properties sincronizzato
	 */
	public synchronized Properties getProp() {
		return p;
	}

	/**
	 * Metodo che ritorna il valore della chiave "key".
	 * 
	 * @param key
	 *            identifica la chiave.
	 * @param defaultValue
	 *            identifica il valore di default
	 * @return ritorna il valore della chiave "key".
	 */
	public synchronized String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	public synchronized String getProperty(String key) {
		return p.getProperty(key);
	}
}
