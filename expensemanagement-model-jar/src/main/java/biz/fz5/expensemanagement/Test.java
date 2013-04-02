/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement;

import biz.fz5.expensemanagement.model.business.RuleFactory;
import biz.fz5.expensemanagement.model.entity.Rule;

/**
 * @author fabiozambelli
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java.util.List<Rule> rules = new RuleFactory().getRules("Total",1);
		for (Rule r : rules)
			r.print();
		
	}

}
