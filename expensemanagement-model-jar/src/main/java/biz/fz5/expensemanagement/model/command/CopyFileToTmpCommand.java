/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.entity.ImageFile;

/**
 * @author fabiozambelli
 *
 */
public class CopyFileToTmpCommand extends CommandConfig implements Command {

	private ImageFile imageFile;
	
	public static final String ID  = "COPY_TO_TMP"; 
	public static final String COMMAND = "cp " + DIR_PUB + "$.jpg " + DIR_TMP + "$.jpg"; 
	
	public CopyFileToTmpCommand (ImageFile imageFile) {
		this.imageFile = imageFile;
	}
	
	public void execute() throws Exception {
		imageFile.doAction(replace(COMMAND,"$",imageFile.getFileName()));
	}
}
