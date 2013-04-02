/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import biz.fz5.expensemanagement.model.hibernate.HibernateSessionFactory;
import biz.fz5.expensemanagement.model.hibernate.dao.ReceiptDAO;
import biz.fz5.expensemanagement.model.hibernate.dao.ReceiptStatusDAO;
import biz.fz5.expensemanagement.model.hibernate.pojo.Receipt;
import biz.fz5.expensemanagement.model.hibernate.pojo.ReceiptStatus;

/**
 * @author fabiozambelli
 *
 */
public class ReceiptManager implements ReceiptInterface {

	private ReceiptDAO receiptDAO = new ReceiptDAO();
	private ReceiptStatusDAO receiptStatusDAO = new ReceiptStatusDAO();
	
	public Receipt create(String idUser, String imagePath) throws Exception {
				
		ReceiptStatus receiptStatus = receiptStatusDAO.findItem(ReceiptStatus.CREATED);
		Receipt receipt = new Receipt(idUser, imagePath, receiptStatus);
		receiptDAO.save(receipt);
		return receipt;
	}

	public Receipt getReceipt(Long id) throws Exception {
		
		return receiptDAO.findItem(id);
	}

	public List<Receipt> getReceipts(String idUser) throws Exception {
		
		List<Receipt> receipts = null;

		Map parameters = new HashMap();
		if ((idUser != null) && (!"".equals(idUser)))
			parameters.put("idUser", idUser);
		
		receipts = receiptDAO.findItems(parameters, "uploadDate", false);		
		
		return receipts;
	}
	
	public Receipt getReceipt() throws Exception {
		
		Receipt receipt = null;
		
		Session session = HibernateSessionFactory.getSession();
		
		try {
			
			HibernateSessionFactory.beginTransaction();
			

			Map parameters = new HashMap();	
			parameters.put("status", ReceiptStatus.CREATED);
			
			List<Receipt> receipts = receiptDAO.findItems(parameters, null, true);
			
			if ( (receipts!=null) && (receipts.size()>0) )
				receipt = receipts.get(0);
			
			receipt.setStatus(receiptStatusDAO.findItem(ReceiptStatus.PROCESSED));
			receiptDAO.save(receipt);
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			HibernateSessionFactory.rollbackTransaction();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
		}
		
		return receipt;
	}
	
	
	public List<Receipt> getReceipts() throws Exception {
		
		List<Receipt> receipts = null;
		
		Session session = HibernateSessionFactory.getSession();
		
		try {
			
			HibernateSessionFactory.beginTransaction();
			

			Map parameters = new HashMap();	
			parameters.put("status", ReceiptStatus.CREATED);
			
			receipts = receiptDAO.findItems(parameters, null, true);
			
			for (Receipt receipt : receipts) {
				receipt.setStatus(receiptStatusDAO.findItem(ReceiptStatus.PROCESSED));
				receiptDAO.save(receipt);
			}
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			HibernateSessionFactory.rollbackTransaction();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
		}
		
		return receipts;
	}

	public void complete(Long id, String total, String date) throws Exception {
		
		Receipt receipt = null;
		
		Session session = HibernateSessionFactory.getSession();
		
		try {
		
			HibernateSessionFactory.beginTransaction();
			
			receipt = receiptDAO.findItem(id);
			ReceiptStatus receiptStatus = receiptStatusDAO.findItem(ReceiptStatus.COMPLETED);
			receipt.setStatus(receiptStatus);
			receipt.setTotal(total);
			receipt.setDate(date);
			receiptDAO.save(receipt);
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
						
			e.printStackTrace();
			HibernateSessionFactory.rollbackTransaction();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
		}
	}

}
