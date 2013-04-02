/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.hibernate.dao;

import biz.fz5.expensemanagement.model.hibernate.BaseHibernateDAO;
import biz.fz5.expensemanagement.model.hibernate.pojo.Receipt;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author fabiozambelli
 *
 */
public class ReceiptDAO extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(ReceiptDAO.class);

	public void save(Receipt transientInstance) {

		log.debug("saving Receipt instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");

		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void attachDirty(Receipt instance) {

		log.debug("attaching dirty Receipt instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Receipt persistentInstance) {
		log.debug("deleting Receipt instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Receipt findItem(Long id) throws Exception {

		Receipt receipt = null;
		try {
			Criteria crit = null;
			crit = getSession().createCriteria(Receipt.class);
			crit.add(Restrictions.eq("id", id));
			receipt = (Receipt) crit.uniqueResult();
		} catch (Exception e) {
			log.error("findItem failed", e);
			throw new Exception(e);
		}
		return receipt;
	}
		
	public List<Receipt> findItems(Map parameters, String orderBy, boolean orderAsc)
			throws Exception {

		List<Receipt> receipts = null;

		try {
			Criteria crit = null;
			crit = getSession().createCriteria(Receipt.class);

			if (!parameters.isEmpty()) {
				if (parameters.containsKey("status")) {
					crit.add(Restrictions.eq("status.id",
							(Integer) parameters.get("status")));
				}		
				if (parameters.containsKey("idUser")) {
					crit.add(Restrictions.eq("idUser",
							(String) parameters.get("idUser")));
				}		
			}

			// ordinamento
			if ((orderBy != null) && (orderAsc))
				crit.addOrder(Order.asc(orderBy));
			if ((orderBy != null) && (!orderAsc))
				crit.addOrder(Order.desc(orderBy));

			receipts = crit.list();

			log.debug("games:" + receipts);

		} catch (Exception e) {
			log.error("findItems failed", e);
			throw new Exception(e);
		}
		return receipts;
	}
}
