/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.entity.ImageFile;

/**
 * @author fabiozambelli
 *
 */
public class ConvertTifCommand extends CommandConfig implements Command {

	private ImageFile imageFile;
	
	public static final String ID  = "CONVERT_TO_TIFF"; 
	public static final String COMMAND = "convert -density 300 " + DIR_TMP + "$.jpg " + DIR_TMP + "$.tif"; 
	
	public ConvertTifCommand (ImageFile imageFile) {
		this.imageFile = imageFile;
	}
	
	public void execute() throws Exception {
		imageFile.doAction(replace(COMMAND,"$",imageFile.getFileName()));
	}
}
