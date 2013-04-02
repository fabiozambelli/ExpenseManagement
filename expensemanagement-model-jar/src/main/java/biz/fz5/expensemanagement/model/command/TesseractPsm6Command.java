/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.entity.ImageFile;

/**
 * @author fabiozambelli
 *
 */
public class TesseractPsm6Command extends CommandConfig implements Command {

	private ImageFile imageFile;
	
	public static final String ID = "TESSERACT_PSM_6"; 
	public static final String COMMAND = "tesseract " + DIR_TMP + "$.tif " + DIR_TMP + "$ -l ita -psm 6"; 
	
	public TesseractPsm6Command (ImageFile imageFile) {
		this.imageFile = imageFile;
	}
	
	public void execute() throws Exception {
		imageFile.doAction(replace(COMMAND,"$",imageFile.getFileName()));
	}
}
