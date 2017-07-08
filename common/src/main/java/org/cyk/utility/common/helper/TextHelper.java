package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.builder.IdentifierBuilder;

import lombok.Getter;

@Singleton
public class TextHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static TextHelper INSTANCE;
	
	public static TextHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new TextHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static interface Get extends Action<String, String> {
		
		String getIdentifier();
		Get setIdentifier(String identifier);
		
		@Getter
		public static class Adapter extends Action.Adapter.Default<String, String> implements Get,Serializable {
			private static final long serialVersionUID = 1L;
			
			protected String identifier;
			
			public Adapter(String identifier) {
				super("Get text", String.class, identifier, String.class, null);
			}
			
			@Override
			public Get setIdentifier(String identifier) {
				this.identifier = identifier;
				return this;
			}
			
			/**/
			
			public static class Default extends Get.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				public Default(String identifier) {
					super(identifier);
				}
				
				@Override
				protected String __execute__() {
					Collection<IdentifierBuilder.StringMapping> collection = IdentifierBuilder.StringMapping.MAP.get(TextHelper.class);
					if(collection==null)
						return "";
					return listenerUtils.getString(collection, new ListenerUtils.StringMethod<IdentifierBuilder.StringMapping>() {
						@Override
						public String execute(IdentifierBuilder.StringMapping listener) {
							return listener.getIdentifierMapping(identifier);
						}
					});
				}
				
			}
		}
		
	}
	
	/**/
	
	
	
	/**/
	
}
