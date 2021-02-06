package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputMask extends AbstractInput<String> implements Serializable {

	private String mask;
	private Boolean autoClear,validateMask;
	
	/**/
	
	/**/
	
	public static final String FIELD_MASK = "mask";
	public static final String FIELD_AUTO_CLEAR = "autoClear";
	public static final String FIELD_VALIDATE_MASK = "validateMask";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<InputMask> implements Serializable {

		@Override
		public void configure(InputMask inputMask, Map<Object, Object> arguments) {
			super.configure(inputMask, arguments);
			if(inputMask.autoClear == null)
				inputMask.autoClear = Boolean.TRUE;
		}
		
		@Override
		protected String __getTemplate__(InputMask inputMask, Map<Object, Object> arguments) {
			return "/input/mask/default.xhtml";
		}
		
		@Override
		protected Class<InputMask> __getClass__() {
			return InputMask.class;
		}
		
		/**/
		
	}
	
	public static InputMask build(Map<Object, Object> arguments) {
		return Builder.build(InputMask.class,arguments);
	}
	
	public static InputMask build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static InputMask buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(InputMask.class, new ConfiguratorImpl());
	}
}