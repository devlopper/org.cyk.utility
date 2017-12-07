package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.userinterface.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MenuNode extends MenuContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	private Menu menu;
	//private MenuNode parent;
	
	/**/
	
	public MenuNode _setPropertyUrl(String pathIdentifier,Object...queryKeyValue){
		return (MenuNode) super._setPropertyUrl(pathIdentifier, queryKeyValue);
	}
	
	public MenuNode _setPropertyUrl(Constant.Action action,Object object,Object...queryKeyValue){
		return (MenuNode) super._setPropertyUrl(action, object, queryKeyValue);
	}
	
	@Override
	public MenuNode _setPropertyIcon(Object icon) {
		return (MenuNode) super._setPropertyIcon(icon);
	}
	
	@Override
	public MenuNode _setLabelPropertyRendered(Object rendered) {
		return (MenuNode) super._setLabelPropertyRendered(rendered);
	}
	
	@Override
	public MenuNode _setPropertyTitleFromLabel() {
		return (MenuNode) super._setPropertyTitleFromLabel();
	}
	
	public MenuContainer getParent() {
		return (MenuContainer) super.getParent();
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends MenuNode> extends Component.Visible.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends MenuNode> extends Component.Visible.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends MenuNode> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<MenuNode> {

		public static class Adapter extends BuilderBase.Adapter.Default<MenuNode> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(MenuNode.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
}
