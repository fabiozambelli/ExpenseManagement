/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
 */
package biz.fz5.expensemanagement.model.hibernate.pojo;

import java.sql.Timestamp;

/**
 * 
 * Dispositivo
 * 
 * @author fabiozambelli
 * 
 */
public class UserDevice implements java.io.Serializable {

	private Long id;
	private String idUser;
	private String idRegistration;

	public UserDevice() {
	}
	
	public UserDevice(String idUser,String idRegistration ) {
		this.idUser = idUser;
		this.idRegistration = idRegistration; 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdRegistration() {
		return idRegistration;
	}

	public void setIdRegistration(String idRegistration) {
		this.idRegistration = idRegistration;
	}

}
