/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author fabiozambelli
 *
 */
public class ImageFile {

	protected static Logger log = Logger.getLogger(ImageFile.class
			.getName());
	
	private String fileName;
	
	public ImageFile(String fileName) {
		
		this.fileName = fileName;
	}
	
	public void doAction(String commandString) throws Exception {
		
			log.debug("> " + commandString);
			Process p = Runtime.getRuntime().exec(commandString);	
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = in.readLine()) != null) {
		    	log.debug("> " + line);
		    }
		    in.close();			
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
