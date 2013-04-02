/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.entity.ImageFile;

/**
 * @author fabiozambelli
 *
 */
public class ConvertContrast0Level60Command extends CommandConfig implements Command {

	private ImageFile imageFile;
	
	public static final String ID = "CONVERT_CONTRAST_0_LEVEL_60"; 
	public static final String COMMAND = "convert " + DIR_TMP + "$.tif -level 10%x60% " + DIR_TMP + "$.tif"; 
	
	public ConvertContrast0Level60Command (ImageFile imageFile) {
		this.imageFile = imageFile;
	}
	
	public void execute() throws Exception {
		imageFile.doAction(replace(COMMAND,"$",imageFile.getFileName()));
	}
}
