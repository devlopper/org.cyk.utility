package org.cyk.utility.__kernel__;

import org.apache.commons.lang3.RegExUtils;

public class Playground {

	public static void main(String[] args) {
		String lines = "Line1\nLine 2\n\n\nAnother one";
		System.out.println("Before--------------------------------");
		System.out.println(lines);
		System.out.println("After----------------------------------");
		System.out.println(RegExUtils.removeAll(lines, "\n\n\n\n"));
	}

}
