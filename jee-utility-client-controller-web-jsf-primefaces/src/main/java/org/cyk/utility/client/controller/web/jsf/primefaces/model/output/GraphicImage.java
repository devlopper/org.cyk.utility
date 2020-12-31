package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GraphicImage extends AbstractFile implements Serializable {

	private Boolean cache,stream;
	private String title,url,alt,library,name;
	private Integer height,width;
	
	/**/
	
	/**/
	
	public static final String FIELD_LIBRARY = "library";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_STREAM = "stream";
	public static final String FIELD_CACHE = "cache";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_URL = "url";
	public static final String FIELD_ALT = "alt";
	public static final String FIELD_HEIGHT = "height";
	public static final String FIELD_WIDTH = "width";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractFile.AbstractConfiguratorImpl<GraphicImage> implements Serializable {

		@Override
		public void configure(GraphicImage image, Map<Object, Object> arguments) {
			if(arguments == null)
				arguments = new HashMap<>();
			if(!arguments.containsKey(FIELD_IS_IMAGE))
				arguments.put(FIELD_IS_IMAGE, Boolean.TRUE);
			super.configure(image, arguments);
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_IS_IMAGE))) {
				if(image.readButton == null) {
					if(image.library == null)
						image.library = "image";
					if(image.name == null)
						image.name = image.readButton == null ? "icon/file_not_found.png" : "icon/text.png";
				}
			}else {
				if(image.library == null)
					image.library = "image";
				if(image.name == null)
					image.name = image.readButton == null ? "icon/file_not_found.png" : "icon/text.png";
			}
			
			if(image.url == null && StringHelper.isBlank(image.library) && image.value == null)
				image.url = StringHelper.get(MapHelper.readByKey(arguments, FIELD_READ_URI));
			
			if(image.title == null && image.labelOutputText != null)
				image.title = image.labelOutputText.getValue();
			if(image.title == null)
				image.title = "Image";
			if(image.alt == null)
				image.alt = image.title;
			if(image.cache == null)
				image.cache = StringHelper.isNotBlank(image.library);
			if(image.stream == null)
				image.stream = Boolean.TRUE;
			if(image.height == null)
				image.height = 100;
			if(image.width == null)
				image.width = 100;	
		}
		
		@Override
		protected String __getTemplate__(GraphicImage outputText, Map<Object, Object> arguments) {
			return "/output/image/default.xhtml";
		}
		
		@Override
		protected Class<GraphicImage> __getClass__() {
			return GraphicImage.class;
		}
		
		/**/
	
	}
	
	public static GraphicImage build(Map<Object, Object> arguments) {
		return Builder.build(GraphicImage.class,arguments);
	}
	
	public static GraphicImage build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static GraphicImage buildFromValue(String value) {
		return GraphicImage.build(GraphicImage.FIELD_VALUE,value);
	}

	static {
		Configurator.set(GraphicImage.class, new ConfiguratorImpl());
	}
	
	/**/
	
	public static interface Listener extends AbstractFile.Listener<GraphicImage> {
		
		public static abstract class AbstractImpl extends AbstractFile.Listener.AbstractImpl<GraphicImage> implements Listener,Serializable {
			
		}
	}
}