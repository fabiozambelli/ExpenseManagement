/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import biz.fz5.expensemanagement.model.business.ReceiptParserComponent;
import biz.fz5.expensemanagement.model.entity.ImageFile;
import biz.fz5.expensemanagement.model.entity.Receipt;

/**
 * @author fabiozambelli
 *
 */
public class ExtractInformationCommand extends CommandConfig implements Command {

	private ImageFile imageFile;
	
	private Receipt receipt;
	
	public static final String ID  = "EXTRACT_INFO"; 
	 
	
	public ExtractInformationCommand (ImageFile imageFile, Receipt receipt) {
		this.imageFile = imageFile;
		this.receipt = receipt;
	}
	
	public void execute() throws Exception {
		ReceiptParserComponent rpc = new ReceiptParserComponent();		
		rpc.parse(receipt, DIR_TMP+imageFile.getFileNameNoExtension()+".txt");		
	}
}