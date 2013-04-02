/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.entity;

/**
 * @author fabiozambelli
 *
 */
public class Rule {

	private String entity;
	private String regex;
	private short level;
	private int matcherGroup;
	
	
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	public int getMatcherGroup() {
		return matcherGroup;
	}
	public void setMatcherGroup(int matcherGroup) {
		this.matcherGroup = matcherGroup;
	}

	public void print(){
		System.out.println("regex:"+regex);
		System.out.println("level:"+level);
		System.out.println("matcherGroup:"+matcherGroup);
	}
}
