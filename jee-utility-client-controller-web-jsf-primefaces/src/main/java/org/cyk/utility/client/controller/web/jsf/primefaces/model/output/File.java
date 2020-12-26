package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class File extends AbstractFile implements Serializable {
	
	/**/
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractFile.AbstractConfiguratorImpl<File> implements Serializable {

		@Override
		public void configure(File file, Map<Object, Object> arguments) {
			super.configure(file, arguments);
					
		}
		
		@Override
		protected String __getTemplate__(File file, Map<Object, Object> arguments) {
			return "/output/file/default.xhtml";
		}
		
		@Override
		protected Class<File> __getClass__() {
			return File.class;
		}
		
		/**/
	}
	
	public static File build(Map<Object, Object> arguments) {
		return Builder.build(File.class,arguments);
	}
	
	public static File build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}

	static {
		Configurator.set(File.class, new ConfiguratorImpl());
	}
	
	/**/
	
	public static interface Listener extends AbstractFile.Listener<File> {
		public static abstract class AbstractImpl extends AbstractFile.Listener.AbstractImpl<File> implements Listener,Serializable {
			
		}
	}
}