/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.command;

import java.util.Hashtable;

/**
 * @author fabiozambelli
 *
 */
public class CommandController {

	Hashtable<String, Command> commands;
	
	public CommandController (){
		commands = new Hashtable<String, Command>();
	}
	
	public void addCommand(String commandName, Command command) {
		commands.put(commandName, command);
	}
	
	public void executeCommand(String commandName) throws Exception {
		Command command = commands.get(commandName);
		command.execute();
	}
}
