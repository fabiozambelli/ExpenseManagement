/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.entity;

import org.apache.log4j.Logger;

/**
 * @author fabiozambelli
 *
 */
public class Rule {

	protected static Logger log = Logger.getLogger(Rule.class
			.getName());
	
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
		log.debug("regex:"+regex);
		log.debug("level:"+level);
		log.debug("matcherGroup:"+matcherGroup);
	}
}
