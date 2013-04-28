/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.util.List;

import biz.fz5.expensemanagement.model.hibernate.pojo.UserDevice;


/**
 * @author fabiozambelli
 *
 */
public interface UserDeviceInterface {

	public UserDevice create(String idUser, String idRegistration);
	
	public void remove(String idUser, String idRegistration);
	
	public UserDevice getUserDevice(Long id);
	
	public UserDevice getUserDevice(String idUser, String idRegistration);
	
	public List<UserDevice> getUserDevices(String idUser);
	
	public List<UserDevice> getUserDevices();
}
