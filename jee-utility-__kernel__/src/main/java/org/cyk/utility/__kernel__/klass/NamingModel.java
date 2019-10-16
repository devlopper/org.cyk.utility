package org.cyk.utility.__kernel__.klass;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class NamingModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String node,layer,subLayer,suffix;
	private Boolean isSuffixedByLayer;
	
	public NamingModel server() {
		setNode("server");
		return this;
	}
	
	public NamingModel client() {
		setNode("client");
		return this;
	}
	
	public NamingModel persistence() {
		setLayer("persistence");
		return this;
	}
	
	public NamingModel business() {
		setLayer("business");
		return this;
	}
	
	public NamingModel representation() {
		setLayer("representation");
		return this;
	}
	
	public NamingModel controller() {
		setLayer("controller");
		return this;
	}
	
	public NamingModel entities() {
		setSubLayer("entities");
		return this;
	}
	
	public NamingModel api() {
		setSubLayer("api");
		return this;
	}
	
	public NamingModel impl() {
		setSubLayer("impl");
		return this;
	}
	
	public NamingModel suffix() {
		String suffix = null;
		if("entities".equals(subLayer)) {
			if("representation".equals(layer))
				suffix = "Dto";					
		}else {
			suffix = StringHelper.applyCase(layer, Case.FIRST_CHARACTER_UPPER);
			if("impl".equals(subLayer))
				suffix = suffix + "Impl";
		}			
		setSuffix(suffix);
		return this;
	}
}