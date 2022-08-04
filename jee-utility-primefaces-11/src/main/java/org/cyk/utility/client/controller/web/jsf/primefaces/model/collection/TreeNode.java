package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObjectAjaxable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TreeNode extends AbstractObjectAjaxable implements Serializable {

	private String collapsedIcon,expandedIcon,icon,type;
	
	public String stringify(Object nodeData) {
		return ((Listener)(listener == null ? TreeNode.Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).stringify(nodeData, this);
	}
	
	/**/
	
	public static final String FIELD_NODE = "node";
	
	public static class ConfiguratorImpl extends AbstractObjectAjaxable.AbstractConfiguratorImpl<TreeNode> implements Serializable {

		@Override
		public void configure(TreeNode treeNode, Map<Object, Object> arguments) {
			super.configure(treeNode, arguments);
			if(StringHelper.isBlank(treeNode.type))
				treeNode.type = "default";
		}
		
		@Override
		protected String __getTemplate__(TreeNode treeNode, Map<Object, Object> arguments) {
			return "/collection/tree/node/default.xhtml";
		}
		
		@Override
		protected Class<TreeNode> __getClass__() {
			return TreeNode.class;
		}
	}

	/**/
	
	/**/
	
	public static interface Listener {
		
		String stringify(Object nodeData,TreeNode treeNode);
		
		/**/
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			
			@Override
			public String stringify(Object nodeData,TreeNode treeNode) {
				return nodeData == null ? "?? NULL ??" : nodeData.toString();
			}
			
			/**/
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {
				public static final TreeNode.Listener INSTANCE = new DefaultImpl();
			}
		}
	}
	
	public static TreeNode build(Map<Object,Object> arguments) {
		return Builder.build(TreeNode.class,arguments);
	}
	
	public static TreeNode build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(TreeNode.class, new ConfiguratorImpl());
	}
}