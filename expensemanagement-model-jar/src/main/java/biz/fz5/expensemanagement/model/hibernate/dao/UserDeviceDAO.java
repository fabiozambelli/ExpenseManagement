/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.hibernate.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import biz.fz5.expensemanagement.model.hibernate.BaseHibernateDAO;
import biz.fz5.expensemanagement.model.hibernate.pojo.UserDevice;

/**
 * @author fabiozambelli
 *
 */
public class UserDeviceDAO extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(UserDeviceDAO.class);

	public void save(UserDevice transientInstance) {

		log.debug("saving UserDevice instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");

		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void attachDirty(UserDevice instance) {

		log.debug("attaching dirty UserDevice instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserDevice persistentInstance) {
		log.debug("deleting UserDevice instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserDevice findItem(Long id) throws Exception {

		UserDevice userDevice = null;
		try {
			Criteria crit = null;
			crit = getSession().createCriteria(UserDevice.class);
			crit.add(Restrictions.eq("id", id));
			userDevice = (UserDevice) crit.uniqueResult();
		} catch (Exception e) {
			log.error("findItem failed", e);
			throw new Exception(e);
		}
		return userDevice;
	}
		
	public List<UserDevice> findItems(Map parameters, String orderBy, boolean orderAsc)
			throws Exception {

		List<UserDevice> userDevices = null;

		try {
			Criteria crit = null;
			crit = getSession().createCriteria(UserDevice.class);

			if (!parameters.isEmpty()) {
				if (parameters.containsKey("status")) {
					crit.add(Restrictions.eq("status.id",
							(Integer) parameters.get("status")));
				}		
				if (parameters.containsKey("idUser")) {
					crit.add(Restrictions.eq("idUser",
							(String) parameters.get("idUser")));
				}
				if (parameters.containsKey("idRegistration")) {
					crit.add(Restrictions.eq("idRegistration",
							(String) parameters.get("idRegistration")));
				}
			}

			// ordinamento
			if ((orderBy != null) && (orderAsc))
				crit.addOrder(Order.asc(orderBy));
			if ((orderBy != null) && (!orderAsc))
				crit.addOrder(Order.desc(orderBy));

			userDevices = crit.list();

			log.debug("userDevices:" + userDevices);

		} catch (Exception e) {
			log.error("findItems failed", e);
			throw new Exception(e);
		}
		return userDevices;
	}
}
