package org.cyk.utility.__kernel__.log;

public enum LogLevel {
	ALL
	,TRACE
	,DEBUG
	,INFO
	,WARN
	,ERROR
	,FATAL
	,OFF
	;
	
	public static LogLevel DEFAULT = INFO;
}