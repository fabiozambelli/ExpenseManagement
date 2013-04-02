/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

import biz.fz5.expensemanagement.model.entity.CandidateElement;
import biz.fz5.expensemanagement.model.entity.Receipt;
import biz.fz5.expensemanagement.model.entity.Rule;

/**
 * @author fabiozambelli
 *
 */
public class ReceiptParserComponent {
	
	private RuleFactory ruleFactory;
	private List<Rule> rules;
	
	public ReceiptParserComponent(){
		ruleFactory = new RuleFactory();
	}

	public void parse (Receipt receipt, String sourcePath) {
		
		File file = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
				
		receipt.setName(sourcePath);
		
		try {
			file = new File(sourcePath);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			
			Pattern pattern = null;
			Matcher matcher = null;
			
			boolean totalFound = false;
			boolean dateFound = false;
			
			while ( (line=bufferedReader.readLine()) != null) {
				
				totalFound = false;
				dateFound = false;
				
				System.out.println("<line>" + line + "</line>");
								
				rules = ruleFactory.getRules("Total", 1);
				if (!totalFinder(rules, line, receipt)) {
					rules = ruleFactory.getRules("Total",2);
					if (!totalFinder(rules, line, receipt)) {
						rules = ruleFactory.getRules("Total",3);
						totalFinder(rules, line, receipt);
					}
				}
				
				rules = ruleFactory.getRules("Date", 1);
				if (!dateFinder(rules, line, receipt)) {
					rules = ruleFactory.getRules("Date",2);
					if (!dateFinder(rules, line, receipt)) {
						rules = ruleFactory.getRules("Date",3);
						dateFinder(rules, line, receipt);
					}
					
				}
				
			}
				
			bufferedReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();	
			} catch (IOException ioe){
				ioe.printStackTrace();
			}
					
		}
		
		//receipt.optimize();
				
	}
	
	
	private boolean totalFinder(List<Rule> rules, String line, Receipt receipt) {
	
		Pattern pattern = null;
		Matcher matcher = null;
		
		boolean totalFound = false;
		
		for (Rule r : rules) {
			
				try {
					
					pattern = Pattern.compile(r.getRegex());
					
					matcher = pattern.matcher(line);
					
					if (matcher.find()) {
						
						System.out.println(r.getEntity() + ": FOUND(L"+r.getLevel()+") " + r.getRegex() + " in " + line);
						
						switch (r.getMatcherGroup()) {
						
							case 345 :							
								receipt.addToTotalCandidate(new CandidateElement(matcher.group(3)+matcher.group(4)+matcher.group(5),r.getLevel()));
								totalFound = true;		
								break;
								
							case 234 :							
								receipt.addToTotalCandidate(new CandidateElement(matcher.group(2)+matcher.group(3)+matcher.group(4),r.getLevel()));
								totalFound = true;
								break;	
								
							case 2345:
								totalFound = true;
								receipt.addToTotalCandidate(new CandidateElement(matcher.group(2)+matcher.group(3)+matcher.group(4)+matcher.group(5),r.getLevel()));
								break;
							
							case 567 :							
								receipt.addToTotalCandidate(new CandidateElement(matcher.group(5)+matcher.group(6)+matcher.group(7),r.getLevel()));
								totalFound = true;		
								break;
							
							case 678 :							
								receipt.addToTotalCandidate(new CandidateElement(matcher.group(6)+matcher.group(7)+matcher.group(8),r.getLevel()));
								totalFound = true;		
								break;
													
						}								
					}				
					
				} catch (PatternSyntaxException pse) {
					
					pse.printStackTrace();
				}			
						
		}
		
		return totalFound;
	}
	
	
	private boolean dateFinder(List<Rule> rules, String line, Receipt receipt) {
		
		Pattern pattern = null;
		Matcher matcher = null;
		
		boolean dateFound = false;
		
		for (Rule r : rules) {
			
				try {
					
					pattern = Pattern.compile(r.getRegex());
					
					matcher = pattern.matcher(line);
					
					if (matcher.find()) {
						
						System.out.println(r.getEntity() + ": FOUND(L"+r.getLevel()+") " + r.getRegex() + " in " + line);
						
						switch (r.getMatcherGroup()) {
						
							case 23456 :
								receipt.addToDateCandidate(new CandidateElement(matcher.group(2)+"/"+matcher.group(4)+"/"+matcher.group(6),r.getLevel()));
								dateFound = true;
								break;
							
							case 234:													
								dateFound = true;
								receipt.addToDateCandidate(new CandidateElement(matcher.group(2)+"/"+matcher.group(4),r.getLevel()));
								break;	
						}								
					}				
					
				} catch (PatternSyntaxException pse) {
					
					pse.printStackTrace();
				}				
						
		}
		
		return dateFound;
	}
	
	
	
	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		String SOURCE_PATH = "/Users/fabiozambelli/Temp/ocr/test2c/";
		//String[] SOURCE_FILE = {"IMAG0115.txt", "IMAG0116.txt", "IMAG0117.txt", "IMAG0118.txt", "IMAG0119.txt", "IMAG0120.txt", "IMAG0122.txt", "IMAG0123.txt", "IMAG0126.txt"};
		String[] SOURCE_FILE = {"IMAG0147_1.txt", "IMAG0148_1.txt", "IMAG0149_1.txt", "IMAG0150_1.txt", "IMAG0151_1.txt", "IMAG0152_1.txt", "IMAG0153_1.txt", "IMAG0154_1.txt", "IMAG0155_1.txt"};
		
		ReceiptParserComponent rpc = new ReceiptParserComponent();		
		for (int i=0; i<SOURCE_FILE.length; i++) {
			rpc.parse(new Receipt(), SOURCE_PATH+SOURCE_FILE[i]);			
		}
					

	}
	
}
