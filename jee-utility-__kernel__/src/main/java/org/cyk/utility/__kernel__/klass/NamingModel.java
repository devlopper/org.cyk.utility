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
	private Boolean isSuffixedByLayer,systemIdentifiable,businessIdentifiable,namable;
	
	public NamingModel server() {
		setNode(NODE_SERVER);
		return this;
	}
	
	public NamingModel client() {
		setNode(NODE_CLIENT);
		return this;
	}
	
	public Boolean isNodeServer() {
		return NODE_SERVER.equals(node);
	}
	
	public Boolean isNodeClient() {
		return NODE_SERVER.equals(node);
	}
	
	public NamingModel persistence() {
		setLayer(LAYER_PERSISTENCE);
		return this;
	}
	
	public NamingModel business() {
		setLayer(LAYER_BUSINESS);
		return this;
	}
	
	public NamingModel representation() {
		setLayer(LAYER_REPRESENTATION);
		return this;
	}
	
	public NamingModel controller() {
		setLayer(LAYER_CONTROLLER);
		return this;
	}
	
	public Boolean isLayerPersistence() {
		return LAYER_PERSISTENCE.equals(layer);
	}
	
	public Boolean isLayerBusiness() {
		return LAYER_BUSINESS.equals(layer);
	}
	
	public Boolean isLayerRepresentation() {
		return LAYER_REPRESENTATION.equals(layer);
	}
	
	public Boolean isLayerController() {
		return LAYER_CONTROLLER.equals(layer);
	}
	
	public NamingModel entities() {
		setSubLayer(SUB_LAYER_ENTITIES);
		return this;
	}
	
	public NamingModel api() {
		setSubLayer(SUB_LAYER_API);
		return this;
	}
	
	public NamingModel impl() {
		setSubLayer(SUB_LAYER_IMPL);
		return this;
	}
	
	public NamingModel suffix() {
		String suffix = null;
		if(SUB_LAYER_ENTITIES.equals(subLayer)) {
			if(LAYER_REPRESENTATION.equals(layer))
				suffix = "Dto";					
		}else {
			suffix = StringHelper.applyCase(layer, Case.FIRST_CHARACTER_UPPER);
			if(SUB_LAYER_IMPL.equals(subLayer))
				suffix = suffix + "Impl";
		}			
		setSuffix(suffix);
		return this;
	}
	
	public NamingModel systemIdentifiable() {
		setSystemIdentifiable(Boolean.TRUE);
		return this;
	}
	
	public NamingModel businessIdentifiable() {
		setBusinessIdentifiable(Boolean.TRUE);
		return this;
	}
	
	public NamingModel namable() {
		setNamable(Boolean.TRUE);
		return this;
	}
	
	public Boolean isSubLayerEntities() {
		return SUB_LAYER_ENTITIES.equals(subLayer);
	}
	
	public Boolean isSubLayerApi() {
		return SUB_LAYER_API.equals(subLayer);
	}
	
	public Boolean isSubLayerImpl() {
		return SUB_LAYER_IMPL.equals(subLayer);
	}
	
	/**/
	
	public static final String NODE_SERVER = "server";
	public static final String NODE_CLIENT = "client";
	
	public static final String LAYER_PERSISTENCE = "persistence";
	public static final String LAYER_BUSINESS = "business";
	public static final String LAYER_REPRESENTATION = "representation";
	public static final String LAYER_CONTROLLER = "controller";
	
	public static final String SUB_LAYER_ENTITIES = "entities";
	public static final String SUB_LAYER_API = "api";
	public static final String SUB_LAYER_IMPL = "impl";
}