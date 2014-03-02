package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class StringHelper implements Serializable {

	private static final long serialVersionUID = 2366347884051000495L;

	public static final String[] END_LINE = {"\r\n","\n"};
	//private static final Logger LOGGER = Logger.getLogger(StringUtils.class.getName());
	
	public enum CaseType{NONE,FURL}
	/*		
	public static String letterCase(String string,CaseType caseType){
		switch(caseType){
		case NONE:return string;
		case FURL:return WordUtils.c
			if(line.length()>1)
				s.append((line.charAt(0)+"").toUpperCase()+line.substring(1).toLowerCase());
			else
				s.append(line);
			s.append("\r\n");
			break;
		}
			
	}
	*/
	public static String removeEndLineMarker(String line){
		for(String endLine : END_LINE)
			if(line.endsWith(endLine))
				return line.substring(0, line.length()-endLine.length());
		return line;
	}
		
	public static void main(String args[]){
		//System.out.println(letterCase("AB\r\nc\n\n\nhEllo", CaseType.FURL));
	}
	
}
