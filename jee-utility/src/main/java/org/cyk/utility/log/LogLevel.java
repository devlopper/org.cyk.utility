package org.cyk.utility.log;

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