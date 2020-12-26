package org.cyk.utility.javascript;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface OpenWindowScriptBuilder extends AbstractScriptBuilder {

	String build(Arguments arguments);
	String build(String uri,String title,String identifier);
	String build(String uri,String title);
	String build(String uri);
	
	public static abstract class AbstractImpl extends AbstractObject implements OpenWindowScriptBuilder,Serializable {
		@Override
		public String build(Arguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("URI", arguments.uri);
			String identifier = arguments.identifier;
			if(StringHelper.isBlank(identifier))
				identifier = RandomHelper.getAlphabetic(6);
			
			String parameters = arguments.parameters;
			if(StringHelper.isBlank(parameters))
				parameters = "location=no,menubar=no,resizable=no,status=no,titlebar=no,toolbar=no";
			
			String title = arguments.title;
			if(StringHelper.isBlank(title))
				title = identifier;
			
			return String.format(FORMAT, arguments.uri,identifier,parameters,title);
		}
		
		@Override
		public String build(String uri, String title, String identifier) {
			return build(new Arguments().setIdentifier(identifier).setUri(uri).setTitle(title));
		}
		
		@Override
		public String build(String uri, String title) {
			return build(uri, title, title);
		}
		
		@Override
		public String build(String uri) {
			return build(uri, uri);
		}
	}
	
	/**/
	
	String FORMAT = "var w = window.open('%s','%s','%s');w.document.title = '%s';";
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private String title,uri,identifier,parameters;
	}
	
	static OpenWindowScriptBuilder getInstance() {
		return Helper.getInstance(OpenWindowScriptBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}
