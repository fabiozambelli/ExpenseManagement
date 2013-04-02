/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author fabiozambelli
 *
 */
public class ImageFile {

	private String fileName;
	
	public ImageFile(String fileName) {
		
		this.fileName = fileName;
	}
	
	public void doAction(String commandString) throws Exception {
		
			System.out.println("> " + commandString);
			Process p = Runtime.getRuntime().exec(commandString);	
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = in.readLine()) != null) {
		         System.out.println("> " + line);
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
