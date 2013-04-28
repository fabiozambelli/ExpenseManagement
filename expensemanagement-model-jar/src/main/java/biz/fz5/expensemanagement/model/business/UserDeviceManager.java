/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import biz.fz5.expensemanagement.model.hibernate.HibernateSessionFactory;
import biz.fz5.expensemanagement.model.hibernate.dao.UserDeviceDAO;
import biz.fz5.expensemanagement.model.hibernate.pojo.UserDevice;

/**
 * @author fabiozambelli
 *
 */
public class UserDeviceManager implements UserDeviceInterface {

	private UserDeviceDAO userDeviceDAO = new UserDeviceDAO();
	
	public UserDevice create(String idUser, String idRegistration) {
		
		Session session = HibernateSessionFactory.getSession();
		
		UserDevice userDevice = null;
		try {
			
			HibernateSessionFactory.beginTransaction();
			
			userDevice = new UserDevice(idUser, idRegistration);
			userDeviceDAO.save(userDevice);
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			HibernateSessionFactory.rollbackTransaction();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
		}
		
		return userDevice;
	}
	
	public void remove(String idUser, String idRegistration) {
		
		Session session = HibernateSessionFactory.getSession();
		
		try {
			
			HibernateSessionFactory.beginTransaction();
			
			UserDevice userDevice = getUserDevice(idUser, idRegistration);
			if (userDevice!=null) {			
				userDeviceDAO.delete(userDevice);	
			}
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			HibernateSessionFactory.rollbackTransaction();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
		}

							
	}

	public UserDevice getUserDevice(Long id) {
		
		UserDevice userDevice = null;
		try {
			
			userDevice = userDeviceDAO.findItem(id);
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return userDevice;
	}

	public UserDevice getUserDevice(String idUser, String idRegistration) {
		
		List<UserDevice> userDevices = null;
		UserDevice userDevice = null;

		Map parameters = new HashMap();
		if ((idUser != null) && (!"".equals(idUser)))
			parameters.put("idUser", idUser);
		if ((idRegistration != null) && (!"".equals(idRegistration)))
			parameters.put("idRegistration", idRegistration);
		
		Session session = HibernateSessionFactory.getSession();
		
		try {
			
			
			HibernateSessionFactory.beginTransaction();
			
			userDevices = userDeviceDAO.findItems(parameters, null, false);
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
			
		}
				
		
		if ( (userDevices!=null) && (userDevices.size()>0) )
			userDevice = userDevices.get(0);
		
		return userDevice;
	}

	public List<UserDevice> getUserDevices(String idUser) {
		
		List<UserDevice> userDevices = null;

		Map parameters = new HashMap();
		if ((idUser != null) && (!"".equals(idUser)))
			parameters.put("idUser", idUser);
		
		Session session = HibernateSessionFactory.getSession();
		
		try {
		
			HibernateSessionFactory.beginTransaction();
					
			userDevices = userDeviceDAO.findItems(parameters, null, false);
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
			
		}
				
		
		return userDevices;
	}
	
	public List<UserDevice> getUserDevices() {
		
		List<UserDevice> userDevices = null;

		Map parameters = new HashMap();

		Session session = HibernateSessionFactory.getSession();
		
		try {
			
			HibernateSessionFactory.beginTransaction();
			
			userDevices = userDeviceDAO.findItems(parameters, null, false);
			
			HibernateSessionFactory.commitTransaction();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			HibernateSessionFactory.closeSession();
			
		}
					
		
		return userDevices;
	}


}
