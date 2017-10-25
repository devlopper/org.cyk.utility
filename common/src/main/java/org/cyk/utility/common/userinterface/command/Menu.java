package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.Container;

public class Menu extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum RenderType{PLAIN,SLIDE,PANEL,TAB,BREAD_CRUMB,BAR}
	
	/**/
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Menu> extends Container.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Menu> extends Container.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Menu> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Menu> {

		public static class Adapter extends BuilderBase.Adapter.Default<Menu> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Menu.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
}
