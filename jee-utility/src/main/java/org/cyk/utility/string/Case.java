package org.cyk.utility.string;

public enum Case {
	NONE
	, LOWER
	, UPPER
	, FIRST_CHARACTER_LOWER
	, FIRST_CHARACTER_UPPER
	, FIRST_CHARACTER_UPPER_REMAINDER_LOWER
	;

	public static Case DEFAULT = NONE;
}