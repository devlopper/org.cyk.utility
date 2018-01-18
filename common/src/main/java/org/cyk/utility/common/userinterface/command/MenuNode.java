package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.hierarchy.MenuNodesContainer;

@Getter @Setter @Accessors(chain=true)
public class MenuNode extends MenuNodesContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	private Menu menu;
	
	/**/
	
	/**/
	
	@Override
	public String toString() {
		return label == null || label.getPropertiesMap().getValue() == null ? super.toString() :  (String)label.getPropertiesMap().getValue();
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
