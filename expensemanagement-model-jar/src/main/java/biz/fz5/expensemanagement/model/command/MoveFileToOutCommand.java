/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.entity.ImageFile;

/**
 * @author fabiozambelli
 *
 */
public class MoveFileToOutCommand extends CommandConfig implements Command {

	private ImageFile imageFile;
	
	public static final String ID  = "MOVE_TO_OUT"; 
	public static final String COMMAND = "mv " + DIR_TMP + "$.* " + DIR_OUT + "$.*"; 
	
	public MoveFileToOutCommand (ImageFile imageFile) {
		this.imageFile = imageFile;
	}
	
	public void execute() throws Exception {
		imageFile.doAction(replace(COMMAND,"$",imageFile.getFileName()));
	}
}
