package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class BlockUI extends AbstractObject implements Serializable {
	
	private String trigger,block;
	private Boolean blocked=Boolean.FALSE,animate=Boolean.TRUE;
	
	/**/
	
	/**/
	
	public static final String FIELD_TRIGGER = "trigger";
	public static final String FIELD_BLOCK = "block";
	public static final String FIELD_BLOCKED = "blocked";
	public static final String FIELD_ANIMATE = "animate";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<BLOCK_UI extends BlockUI> extends AbstractObject.AbstractConfiguratorImpl<BLOCK_UI> implements Serializable {
		
		@Override
		public void configure(BLOCK_UI blockUI, Map<Object, Object> arguments) {
			super.configure(blockUI, arguments);
			
		}
		
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<BlockUI> implements Serializable {

		@Override
		protected Class<BlockUI> __getClass__() {
			return BlockUI.class;
		}

		@Override
		protected String __getTemplate__(BlockUI object, Map<Object, Object> arguments) {
			return "/blockui/default.xhtml";
		}
	}
	
	static {
		Configurator.set(BlockUI.class, new ConfiguratorImpl());
	}
}
