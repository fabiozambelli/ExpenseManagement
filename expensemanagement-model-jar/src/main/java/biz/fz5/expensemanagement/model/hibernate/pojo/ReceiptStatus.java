/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.hibernate.pojo;

/**
 * Stati dello scontrino
 * 
 * @author fabiozambelli
 * 
 */
public class ReceiptStatus implements java.io.Serializable {

	public static final Integer CREATED = 1;
	public static final Integer PROCESSED = 2;
	public static final Integer COMPLETED = 3;

	private static final long serialVersionUID = -4124348155768904879L;

	private Integer id;
	private String description;

	public ReceiptStatus() {
	}

	public ReceiptStatus(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
