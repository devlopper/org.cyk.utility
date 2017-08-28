package org.cyk.utility.common;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		//org.apache.commons.beanutils.PropertyUtils.getn
		
		try {
			Runtime.getRuntime().exec(  new String [] {"D:\\mysql-5.6.26-winx64\\bin\\mysql", "-u", "root", "-proot", "show databases"} );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
