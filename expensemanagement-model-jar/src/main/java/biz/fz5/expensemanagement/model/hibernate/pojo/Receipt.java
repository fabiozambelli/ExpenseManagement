/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.hibernate.pojo;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 *
 * Scontrino
 * 
 * @author fabiozambelli
 * 
 */
public class Receipt implements java.io.Serializable {

	private static final long serialVersionUID = 7287451307194381432L;

	private Long id;
	private String idUser;
	private String name;
	private Blob imageReceipt;
	private String total;
	private String date;
	private String tag;
	private Timestamp uploadDate;
	private ReceiptStatus status;

	public Receipt(){}
	
	public Receipt(String idUser, String name, Blob imageReceipt, String tag, ReceiptStatus status) {
		this.imageReceipt = imageReceipt;
		this.name = name;
		this.idUser = idUser;
		this.status = status;
		this.tag = tag;
		this.uploadDate = new Timestamp(System.currentTimeMillis());
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Blob getImageReceipt() {
		return imageReceipt;
	}

	public void setImageReceipt(Blob imageReceipt) {
		this.imageReceipt = imageReceipt;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	public ReceiptStatus getStatus() {
		return status;
	}

	public void setStatus(ReceiptStatus status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	
}
