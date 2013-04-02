/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.entity;

/**
 * @author fabiozambelli
 *
 */
public class CandidateElement {
	
	public CandidateElement(String value, short weight) {
		this.value = value;
		this.weight = weight;
	}
	
	String value;
	short weight;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}
	
	

}
