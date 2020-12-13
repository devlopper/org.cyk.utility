package org.cyk.utility.report;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.file.FileType;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ReportGetter {

	ByteArrayOutputStream get(Arguments arguments);
	
	ByteArrayOutputStream get(String filePath,Map<Object,Object> parameters,FileType fileType);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ReportGetter,Serializable {
		
		@Override
		public ByteArrayOutputStream get(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			String filePath = ValueHelper.throwIfBlank("report file path", arguments.getFilePath());
			Map<Object,Object> parameters = arguments.getParameters();
			FileType fileType = ValueHelper.defaultToIfNull(arguments.getFileType(), FileType.PDF);
			if(Boolean.TRUE.equals(LOGGABLE))
				LogHelper.log(String.format("build report. relative uri = %s | parameters = %s | file type = %s", filePath,parameters,fileType),LOG_LEVEL, getClass());				
			return __getFromServer__(filePath,parameters,fileType);
		}
		
		protected abstract ByteArrayOutputStream __getFromServer__(String filePath,Map<Object,Object> parameters,FileType fileType);
		
		@Override
		public ByteArrayOutputStream get(String filePath, Map<Object, Object> parameters,FileType fileType) {
			return get(new Arguments().setFilePath(filePath).setParameters(parameters).setFileType(fileType).setSource(Arguments.Source.SERVER));
		}
		
		protected void throwNotFound(String filePath) {
			throw new RuntimeException().addMessages(new Message().setIdentifier(ReportGetter.THROWABLE_NOT_FOUND_IDENTIFIER)
					.setSummary(String.format(THROWABLE_NOT_FOUND_SUMMARY_FORMAT, filePath)));
		}
		
		public static Boolean LOGGABLE = Boolean.TRUE;
		public static Level LOG_LEVEL = Level.FINEST;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String filePath;
		private Map<Object, Object> parameters;
		private FileType fileType;
		private Source source;
		
		public static enum Source {
			SERVER,CLIENT
		}
	}
	
	/**/
	
	static ReportGetter getInstance() {
		return Helper.getInstance(ReportGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String THROWABLE_NOT_FOUND_IDENTIFIER = "REPORT_NOT_FOUND";
	String THROWABLE_NOT_FOUND_SUMMARY_FORMAT = "Report %s not found";
}