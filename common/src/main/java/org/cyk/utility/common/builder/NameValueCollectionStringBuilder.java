package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtension;
import org.cyk.utility.common.ListenerUtils;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class NameValueCollectionStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
		
	public static String ENCODED_PARAMETER_NAME = "encoded";
	
	private Collection<NameValueStringBuilder> collection = new ArrayList<>();
	private String separator;
	private FileExtension fileExtension;
	
	public NameValueCollectionStringBuilder add(NameValueStringBuilder...namesValues){
		for(NameValueStringBuilder nameValue : namesValues)
			getCollection().add(nameValue);
		return this;
	}
	
	public NameValueCollectionStringBuilder addNameValue(Object name,Object value){
		return add(new NameValueStringBuilder(name, value));
	}
		
	public NameValueCollectionStringBuilder addNamesValues(Object...values){
		for(int i = 0 ; i < values.length ; i = i+2){
			addNameValue(values[i], values[i+1]);
		}
		return this;
	}
	
	public NameValueCollectionStringBuilder addFiles(final Collection<?> files){
		FileExtension fileExtension = listenerUtils.getValue(FileExtension.class, Listener.COLLECTION, new ListenerUtils.ResultMethod<Listener, FileExtension>(){
			@Override
			public FileExtension execute(Listener listener) {
				return listener.getFileExtension(files);
			}

			@Override
			public FileExtension getNullValue() {
				return NameValueCollectionStringBuilder.this.fileExtension;
			}
		});
		
		if(fileExtension==null)
			fileExtension = FileExtension.PDF;
		
		return addNamesValues(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
			@Override
			public String execute(Listener listener) {
				return listener.getFileExtensionName();
			}
		}),fileExtension
				,listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getIdentifiableName();
					}
				}),files);
	}
	
	public NameValueCollectionStringBuilder addDialog(){
		return addNamesValues(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getWindowsModeName();
					}
				}),listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
					@Override
					public String execute(Listener listener) {
						return listener.getWindowsModeDialogName();
					}
				})
				);
	} 
	
	@Override
	public String buildWhenBlank() {
		StringBuilder stringBuilder = new StringBuilder();
		
		Collection<Object> encodedParameterNames = new ArrayList<>();
		final List<String> values = new ArrayList<>();
		for(NameValueStringBuilder nameValue : getCollection()){
			String r = nameValue.build();
			if(StringUtils.isNotBlank(r)){
				values.add(r);
				if(Boolean.TRUE.equals(nameValue.getEncoded()))
					encodedParameterNames.add(nameValue.getResultName());	
			}
		}
		if(!encodedParameterNames.isEmpty()){
			NameValueStringBuilder encodedParameterNameStringBuilder = new NameValueStringBuilder(listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
				@Override
				public String execute(Listener listener) {
					return listener.getEncodedParameterName();
				}
				public String getNullValue() {
					return ENCODED_PARAMETER_NAME;
				}
			}));
			values.add(encodedParameterNameStringBuilder.addCollection(encodedParameterNames).build());
		}
		
		stringBuilder.append(StringUtils.join(values,listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
			@Override
			public String execute(Listener listener) {
				return listener.getSeparator();
			}
			@Override
			public String getNullValue() {
				return StringUtils.defaultIfBlank(separator, Constant.CHARACTER_SPACE.toString());
			}
		})));
		
		return stringBuilder.toString();
	}
		
	/**/
	
	/**/
	
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		String getEncodedParameterName();
		String getFileExtensionName();
		FileExtension getFileExtension(Collection<?> files);
		String getIdentifiableName();
		String getWindowsModeName();
		String getWindowsModeDialogName();
		
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			@Override
			public String getEncodedParameterName() {
				return null;
			}
			
			@Override
			public String getFileExtensionName() {
				return null;
			}
			
			@Override
			public FileExtension getFileExtension(Collection<?> files) {
				return null;
			}
			
			@Override
			public String getIdentifiableName() {
				return null;
			}
			
			@Override
			public String getWindowsModeName() {
				return null;
			}
			
			@Override
			public String getWindowsModeDialogName() {
				return null;
			}
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
}