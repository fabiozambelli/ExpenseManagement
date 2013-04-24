/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import biz.fz5.expensemanagement.model.hibernate.pojo.Receipt;

/**
 * @author fabiozambelli
 *
 */
public class ReceiptConsumerJob implements Job {

	protected static Logger log = Logger.getLogger(ReceiptConsumerJob.class
			.getName());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
		
			JobKey jobKey = context.getJobDetail().getKey();
	        log.debug("ReceiptConsumerJob says: " + jobKey + " executing at " + new Date());
	        
	        ReceiptInterface receiptManager = new ReceiptManager();
	        List<Receipt> receiptPojoList =  receiptManager.getReceipts();
	        
	        for (Receipt receiptPojo : receiptPojoList) {
	        	
	        	OCRManager ocrManager = new OCRManager(receiptPojo);
	        	
		        ocrManager.performOCR();
		        
		        receiptManager.complete(receiptPojo.getId(), receiptPojo.getTotal(), receiptPojo.getDate());
	        }
	        	       	        
	        
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new JobExecutionException(e);
		}
		
        
	}

}
