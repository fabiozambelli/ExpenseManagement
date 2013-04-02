/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.util.List;

import biz.fz5.expensemanagement.model.hibernate.pojo.Receipt;

/**
 * @author fabiozambelli
 *
 */
public interface ReceiptInterface {

	public Receipt create(String idUser, String imagePath) throws Exception;
	
	public void complete(Long id, String total, String date) throws Exception;

	public Receipt getReceipt(Long id) throws Exception;
	
	public Receipt getReceipt() throws Exception;
	
	public List<Receipt> getReceipts() throws Exception;
	
	public List<Receipt> getReceipts(String idUser) throws Exception;
}
