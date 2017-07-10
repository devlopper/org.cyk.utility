package org.cyk.utility.common.builder.javascript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.builder.NameValueCollectionStringBuilder;
import org.cyk.utility.common.builder.TextStringBuilder;
import org.cyk.utility.common.builder.UrlStringBuilder;
import org.cyk.utility.common.helper.InstanceHelper;

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
	private Boolean replaced,showMenuBar=Boolean.FALSE,showStatusBar=Boolean.FALSE,showTitleBar=Boolean.FALSE,showToolBar=Boolean.FALSE,showScrollbars=Boolean.FALSE
			,isResiable=Boolean.FALSE,showLocation=Boolean.FALSE;

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
	
	public OpenWindowStringBuilder addFiles(Object owner,Collection<?> files){
		Object identifier = inject(InstanceHelper.class).getIdentifier(owner);
		setName(identifier == null ? Constant.EMPTY_STRING : identifier.toString());
		getUrlStringBuilder().addFiles(files);
		return this;
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
		nameValueCollectionStringBuilder.setSeparator(Constant.CHARACTER_COMA.toString()).addNamesValues(TOOLBAR,new TextStringBuilder().setResponse(showToolBar)
				,SCROLLBARS,new TextStringBuilder().setResponse(showScrollbars),RESIZABLE,new TextStringBuilder().setResponse(isResiable)
				,STATUS,new TextStringBuilder().setResponse(showStatusBar),LOCATION,new TextStringBuilder().setResponse(showLocation)
				,MENUBAR,new TextStringBuilder().setResponse(showMenuBar),TOP,topIndex,LEFT,leftIndex,WIDTH,width,HEIGHT,height);
		
		//TODO should depends on windows _top _blank and so on
		getUrlStringBuilder().getQueryStringBuilder().getNameValueCollectionStringBuilder().addDialog();
		
		return String.format(FORMAT, urlStringBuilder.build(),name,nameValueCollectionStringBuilder.build(),new TextStringBuilder().setResponse(replaced).build());
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
	
	private static final String TOOLBAR = "toolbar";
	private static final String SCROLLBARS = "scrollbars";
	private static final String RESIZABLE = "resizable";
	private static final String STATUS = "status";
	private static final String LOCATION = "location";
	private static final String MENUBAR = "menubar";
	
	private static final String TOP = "top";
	private static final String LEFT = "left";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	
	
}