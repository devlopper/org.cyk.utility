package org.cyk.utility.common;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class TestFromMain {

	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(SimpleUT.class);
		System.out.println("TestFromMain.main() : "+result);
	}

}
