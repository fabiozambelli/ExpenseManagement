/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

/**
 * @author fabiozambelli
 *
 */

@XmlRootElement
public class Receipt {
	
	protected static Logger log = Logger.getLogger(Receipt.class
			.getName());
	
	private String name;
	private String total;
	private String date;
	private String status;
	private String tag;
	private String uploadtime;
	
	private List<CandidateElement> totalCandidate = new ArrayList<CandidateElement>();
	private List<CandidateElement> dateCandidate = new ArrayList<CandidateElement>();
	
	
	
	
	public String getUploadtime() {
		if (uploadtime==null)
			uploadtime = "";
		return uploadtime;
	}
	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	public String getName() {
		if (name==null)
			name = "";
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTag() {
		if (tag==null)
			tag = "";
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getTotal() {
		if (total==null)
			total = "";
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDate() {
		if (date==null)
			date = "";
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public void addToTotalCandidate(CandidateElement value) {
		totalCandidate.add(value);
	}
	
	public void addToDateCandidate(CandidateElement value) {
		dateCandidate.add(value);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void print() {
		log.info("+++++++++++++++++++++++ " + name);
		log.info("totalCandidate:");
		for (CandidateElement candidateElement : totalCandidate)
			log.info(candidateElement.getValue() + "-" + candidateElement.getWeight());
		log.info("dateCandidate:");
		for (CandidateElement candidateElement : dateCandidate)
			log.info(candidateElement.getValue() + "-" + candidateElement.getWeight());
		log.info("total:"+total);
		log.info("date:"+date);
		log.info("++++++++++++++++++++++++++++++++ ");		
	}
	
	private String getHighestOccurency(Hashtable<?, Integer> t){

		   String highestOccurency = null;
		   
	       ArrayList<Map.Entry<?, Integer>> l = new ArrayList(t.entrySet());
	       
	       Collections.sort(l, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
	            return o2.getValue().compareTo(o1.getValue());
	        }});
	       
	       if ( (l!=null) && (l.size()>0) )
	    	   highestOccurency = l.get(0).getKey().toString();
	       
	       return highestOccurency;
	    }
	
	private String getHighestValue(Hashtable<?, Integer> t){

		   String highestValue = null;
		   
	       ArrayList<String> l = new ArrayList(t.keySet());
	       
	       Collections.sort(l, new Comparator<String>(){

	         public int compare(String o1, String o2) {
	            return (new Float(o2)).compareTo(new Float(o1));
	        }});
	       
	       if ( (l!=null) && (l.size()>0) )
	    	   highestValue = l.get(0);
	       
	       return highestValue;
	    }
	
	private void updateOccurency (Hashtable<String,Integer> occurency, String value) {
		
		Integer num = occurency.get(value);
		
		if (num==null)
			occurency.put(value, new Integer(1));
		else
			occurency.put(value, num+1);
		
	}
	private List<CandidateElement> getCandidates(int level, List<CandidateElement> fullList) {
		
		List<CandidateElement> candidates = new ArrayList<CandidateElement>();;
		
		for (CandidateElement candidateElement : fullList) {
			
			if (candidateElement.getWeight()==level) {
				
				candidates.add(candidateElement);
			}
		}
		
		return candidates;
	}
	
	private String detectTotal(Hashtable<String,Integer> occurency) {
		String total = null;
		String highestOccurency = null;
		String highestValue = null;
		highestOccurency = getHighestOccurency(occurency);
		highestValue = getHighestValue(occurency);
		if ( (highestOccurency!=null)&&(highestValue!=null)&&(highestValue.equals(highestOccurency)) )
			total = highestOccurency;
		else if ( (highestOccurency!=null)&&(highestValue!=null)&&(!highestValue.equals(highestOccurency)) )
			total = highestOccurency + "|" + highestValue;
		else if ( (highestOccurency!=null)&&(highestValue==null) )
			total = highestOccurency;
		else if ( (highestValue!=null)&&(highestOccurency==null) )
			total = highestValue;
		return total;
	}
	
	public void optimize(){
		
		Hashtable<String,Integer> occurency = new Hashtable<String,Integer>(); 
		
		
		
		for (CandidateElement candidateElement : getCandidates(1, totalCandidate)  ) {
		
			String value = candidateElement.getValue();
			
			try {
				
				value = value.replace(',', '.');
				value = value.trim();
				
				Float.parseFloat(value);
				
				updateOccurency (occurency, value);			
				
			} catch (Exception e) {
											
				e.printStackTrace();
			}
		}	
		
		total = detectTotal(occurency);		
		
		if ( (total==null) || ("".equals(total)) ) {
			
			occurency.clear();
			
			for (CandidateElement candidateElement : getCandidates(2, totalCandidate)  ) {
				
				String value = candidateElement.getValue();
				
				try {
					
					value = value.replace(',', '.');
					value = value.trim();
					
					Float.parseFloat(value);					
					
					updateOccurency (occurency, value);
					
				} catch (Exception e) {
												
					e.printStackTrace();
				}
			}
			
			total = detectTotal(occurency);
		}
			
		if ( (total==null) || ("".equals(total)) ) {
			
			occurency.clear();
			
			for (CandidateElement candidateElement : getCandidates(3, totalCandidate)  ) {
				
				String value = candidateElement.getValue();
				
				try {
					
					value = value.replace(',', '.');
					value = value.trim();
					
					Float.parseFloat(value);
					
					updateOccurency (occurency, value);
					
				} catch (Exception e) {
												
					e.printStackTrace();
				}
			}
			
			total = detectTotal(occurency);
		}
		
		occurency.clear();
		
		for (CandidateElement candidateElement : getCandidates(1, dateCandidate)) {
			
			String value = candidateElement.getValue();
			
			try {			
				
				new SimpleDateFormat("dd/MM/yyyy").parse(value);
				
				updateOccurency (occurency, value);						
				
				
			} catch (Exception e) {
				
				
				e.printStackTrace();
				
			}
	
		
			try {
				
				new SimpleDateFormat("dd/MM/yy").parse(value);
				
				updateOccurency (occurency, value);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
			try {
				
				new SimpleDateFormat("MM/yyyy").parse(value);
				
				if ( ( Integer.parseInt(value.substring(0,2)) < 1 ) || ( Integer.parseInt(value.substring(0,2)) > 12 ) )
					throw new Exception("Invalid Month");
				
				if ( "9".equals(value.substring(4,5)) )
					value.substring(4,5).replace('9', '0');
				
				updateOccurency (occurency, value);
									
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
		}		
		
		date = getHighestOccurency(occurency);
		
		if ( (date==null) || ("".equals(date)) ) {
			
			occurency.clear();
			
			for (CandidateElement candidateElement : getCandidates(2, dateCandidate)) {
				
				String value = candidateElement.getValue();
				
				try {			
					
					new SimpleDateFormat("dd/MM/yyyy").parse(value);
					
					updateOccurency (occurency, value);						
					
					
				} catch (Exception e) {
					
					
					e.printStackTrace();
					
				}
		
			
				try {
					
					new SimpleDateFormat("dd/MM/yy").parse(value);
					
					updateOccurency (occurency, value);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
				try {
					
					new SimpleDateFormat("MM/yyyy").parse(value);
					
					if ( ( Integer.parseInt(value.substring(0,2)) < 1 ) || ( Integer.parseInt(value.substring(0,2)) > 12 ) )
						throw new Exception("Invalid Month");
					
					if ( "9".equals(value.substring(4,5)) )
						value.substring(4,5).replace('9', '0');
					
					updateOccurency (occurency, value);
										
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
			
			date = getHighestOccurency(occurency);
		}
	}

	public static void main(String[] args) {
		Receipt r = new Receipt();
		Hashtable<String,Integer> occurency = new Hashtable<String,Integer>();
		occurency.put("1.2", 1);
		occurency.put("2.3", 3);
		occurency.put("4.5", 2);
		String max = r.getHighestValue(occurency);
		log.info(max);
	}
}
