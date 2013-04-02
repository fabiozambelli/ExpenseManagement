/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.entity.ImageFile;

/**
 * @author fabiozambelli
 *
 */
public class ConvertContrast6Level70Command extends CommandConfig implements Command {
	
	private ImageFile imageFile;
	
	public static final String ID = "CONVERT_CONTRAST_6_LEVEL_70"; 
	public static final String COMMAND = "convert " + DIR_TMP + "$.tif -contrast -contrast -contrast -contrast -contrast -contrast -level 0%x70% " + DIR_TMP + "$.tif"; 
	
	public ConvertContrast6Level70Command (ImageFile imageFile) {
		this.imageFile = imageFile;
	}
	
	public void execute() throws Exception {
		imageFile.doAction(replace(COMMAND,"$",imageFile.getFileName()));
	}

}
