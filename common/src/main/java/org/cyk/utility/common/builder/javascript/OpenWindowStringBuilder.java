package org.cyk.utility.common.builder.javascript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.builder.NameValueCollectionStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class OpenWindowStringBuilder extends AbstractJavascriptStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	private static final String FORMAT = "window.open('%s','%s','%s','%s')";
	
	private String url, name;
	private Integer leftIndex,topIndex,width,height;
	private Boolean replaced,showMenuBar=Boolean.TRUE,showStatusBar=Boolean.TRUE,showTitleBar=Boolean.TRUE,showToolBar=Boolean.TRUE;
	
	public OpenWindowStringBuilder(String name,String url){
		this.name = name;
		this.url = url;
	}
	
	@Override
	public String build() {
		NameValueCollectionStringBuilder nameValueCollectionStringBuilder = new NameValueCollectionStringBuilder();
		nameValueCollectionStringBuilder.setSeparator(Constant.CHARACTER_COMA.toString()).addNamesValues("toolbar",getValue(Boolean.class, showToolBar)
				,"top",topIndex,"left",leftIndex,"width",width,"height",height);
		return String.format(FORMAT, url,name,nameValueCollectionStringBuilder.build(),getValue(Boolean.class, replaced));
	}
	
	private <T> Object getValue(Class<T> aClass,T value){
		if(Boolean.class.equals(aClass))
			return Boolean.TRUE.equals(value) ? "yes" : "no";
		if(Number.class.isAssignableFrom(aClass))
			return value;
		return Constant.EMPTY_STRING;
	}
	
	/**/
	
	/**/
	
	public static interface Listener extends AbstractJavascriptStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractJavascriptStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
}