/**
 * License CC BY | 2013 fabiozambelli.eu | Some Rights Reserved
*/
package biz.fz5.expensemanagement.model.business;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import biz.fz5.expensemanagement.model.entity.Rule;

/**
 * @author fabiozambelli
 *
 */
public class RuleFactory extends DefaultHandler {

	private String tmp_ = null;
	private Rule rule_ = null;
	private List<Rule> rules = new ArrayList<Rule>();
	
	public RuleFactory() {
		
		super();
		
		try {
			
			XMLReader xr = XMLReaderFactory.createXMLReader();
			xr.setContentHandler(this);
			xr.setErrorHandler(this);
			/*
			 FileReader r = new FileReader(RuleFactory.class.getClassLoader()
                     .getResourceAsStream("rules.xml").getPath());
			 */
			InputStreamReader r = new InputStreamReader(RuleFactory.class.getClassLoader()
                     .getResourceAsStream("rules.xml"));
			 xr.parse(new InputSource(r));
			 
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void startElement (String uri, String name, String qName, Attributes atts) {
		
		if ("rule".equalsIgnoreCase(qName)) {
			rule_ = new Rule();
		}		
	}

	public void endElement (String uri, String name, String qName) {
		
		if ("regex".equalsIgnoreCase(qName)) {
			rule_.setRegex(tmp_);
		} else if ("level".equalsIgnoreCase(qName)) {
			rule_.setLevel(Short.parseShort(tmp_));
		} else if ("matcherGroup".equalsIgnoreCase(qName)) {
			rule_.setMatcherGroup(Integer.parseInt(tmp_));
		} else if ("entity".equalsIgnoreCase(qName)) {
			rule_.setEntity(tmp_);
		} else if ("rule".equalsIgnoreCase(qName)) {
			rules.add(rule_);
		}
    }
	
	public void characters (char[] ch, int start, int length) {
	
		tmp_ = new String(ch, start, length);
			
    }

	public List<Rule> getRules(String entity, int level) {
		
		List<Rule> rules_ = new ArrayList<Rule>();
		for (Rule r : rules) {
			if ( (entity.equals(r.getEntity())) && (r.getLevel()==level) )
				rules_.add(r);
		}
			
		return rules_;
	}
	
	
}
