/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.hibernate.dao;

import biz.fz5.expensemanagement.model.hibernate.BaseHibernateDAO;
import biz.fz5.expensemanagement.model.hibernate.pojo.ReceiptStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author fabiozambelli
 *
 */
public class ReceiptStatusDAO extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(ReceiptStatusDAO.class);

	public ReceiptStatus findItem(Integer id) throws Exception {

		ReceiptStatus receiptStatus = null;
		
		try {
			Criteria crit = null;
			crit = getSession().createCriteria(ReceiptStatus.class);
			crit.add(Restrictions.eq("id", id));
			receiptStatus = (ReceiptStatus) crit.uniqueResult();

		} catch (Exception e) {
			log.error("findItem failed", e);
			throw new Exception(e);
		}
		return receiptStatus;
	}
	
	public ReceiptStatus findItemByDescription(String description) throws Exception {

		ReceiptStatus receiptStatus = null;
		
		try {
			Criteria crit = null;
			crit = getSession().createCriteria(ReceiptStatus.class);
			crit.add(Restrictions.eq("description", description));
			receiptStatus = (ReceiptStatus) crit.uniqueResult();

		} catch (Exception e) {
			log.error("findItem failed", e);
			throw new Exception(e);
		}
		return receiptStatus;
	}

}
