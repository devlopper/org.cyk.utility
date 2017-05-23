package org.cyk.utility.common.builder.javascript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.builder.NameValueCollectionStringBuilder;
import org.cyk.utility.common.builder.TextStringBuilder;
import org.cyk.utility.common.builder.UrlStringBuilder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class OpenWindowStringBuilder extends AbstractJavascriptStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	private static final String FORMAT = "window.open('%s','%s','%s','%s')";
	
	private UrlStringBuilder urlStringBuilder;
	private String name;
	private Integer leftIndex,topIndex,width,height;
	private Boolean replaced,showMenuBar=Boolean.TRUE,showStatusBar=Boolean.TRUE,showTitleBar=Boolean.TRUE,showToolBar=Boolean.TRUE;
	
	public OpenWindowStringBuilder(String name,String url){
		this.name = name;
		getUrlStringBuilder().setInstance(url);
	}
	
	public OpenWindowStringBuilder(String name){
		this(name,null);
	}
	
	public UrlStringBuilder getUrlStringBuilder(){
		if(urlStringBuilder==null)
			urlStringBuilder = new UrlStringBuilder();
		return urlStringBuilder;
	}
	
	@Override
	public String buildWhenBlank() {
		NameValueCollectionStringBuilder nameValueCollectionStringBuilder = new NameValueCollectionStringBuilder();
		if(showToolBar==null)
			showToolBar = Boolean.TRUE;
		if(leftIndex==null)
			leftIndex = 200;
		if(topIndex==null)
			topIndex=50;
		if(width==null)
			width = 900;
		if(height==null)
			height = 500;
		nameValueCollectionStringBuilder.setSeparator(Constant.CHARACTER_COMA.toString()).addNamesValues("toolbar",inject(TextStringBuilder.class).setResponse(showToolBar).build()
				,"top",topIndex,"left",leftIndex,"width",width,"height",height);
		
		//TODO should depends on windows _top _blank and so on
		getUrlStringBuilder().getQueryStringBuilder().getNameValueCollectionStringBuilder().addDialog();
		
		return String.format(FORMAT, urlStringBuilder.build(),name,nameValueCollectionStringBuilder.build(),inject(TextStringBuilder.class).setResponse(replaced).build());
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