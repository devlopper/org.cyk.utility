package org.cyk.jee.utility.client.controller.web.jsf.primefaces;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestBiGdec {

	public static void main(String[] args) {
		String n = "0.42346";
		System.out.println(n+" : "+new BigDecimal(n).setScale(2, RoundingMode.HALF_UP));
		n = "0.42546";
		System.out.println(n+" : "+new BigDecimal(n).setScale(2, RoundingMode.HALF_UP));
		n = "0.42946";
		System.out.println(n+" : "+new BigDecimal(n).setScale(2, RoundingMode.HALF_UP));
		
		n = "170500";
		System.out.println("Taux : ("+n+"/1000000)x100");
		System.out.println("DIV 1 : "+(new BigDecimal(n).divide(new BigDecimal("1000000"))));
		System.out.println("DIV 2 : "+(new BigDecimal(n).divide(new BigDecimal("1000000")).multiply(new BigDecimal("100"))));
		System.out.println("1 : "+(new BigDecimal(n).divide(new BigDecimal("1000000"),2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))));
		System.out.println("2 : "+(new BigDecimal(n).divide(new BigDecimal("1000000")).multiply(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP));
	}

}
