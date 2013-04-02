/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

/**
 * @author fabiozambelli
 *
 */
public interface Command {
	
	public void execute() throws Exception;
}
