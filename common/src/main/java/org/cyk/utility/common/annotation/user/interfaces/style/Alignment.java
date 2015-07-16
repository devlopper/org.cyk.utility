package org.cyk.utility.common.annotation.user.interfaces.style;

public @interface Alignment {

	public static enum Horizontal{
		AUTO,
		LEFT,
		MIDDLE,
		RIGHT,
		JUSTIFY
	}
	
	public static enum Vertical{
		AUTO,
		TOP,
		MIDDLE,
		BOTTOM,
		JUSTIFY
	}
	
	Horizontal horizontal() default Horizontal.AUTO;
	Vertical vertical() default Vertical.AUTO;
	
}
