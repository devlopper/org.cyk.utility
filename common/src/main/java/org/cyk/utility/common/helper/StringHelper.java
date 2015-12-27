package org.cyk.utility.common.helper;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;

public class StringHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	
	public enum CaseType{NONE,FURL}
			
	public String applyCaseType(String string,CaseType caseType){
		switch(caseType){
		case NONE:return string;
		case FURL:return StringUtils.capitalize(string.toLowerCase());
		}
		return string;	
	}
	
	public String removeEndLineMarker(String line){
		for(String endLine : END_LINE)
			if(line.endsWith(endLine))
				return line.substring(0, line.length()-endLine.length());
		return line;
	}
	
	/**/
	
	private static final StringHelper INSTANCE = new StringHelper();
	public static StringHelper getInstance() {
		return INSTANCE;
	}
	
}
