package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Layout;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Container extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout = new Layout();
	
	/**/

	public Container layOut(Visible component){
		addOneChild(component);
		layout.addOneChild(component);
		return this;
	}
	
	public Container layOutBreak(){
		layout.end();
		return this;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Container> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {

		public static class Adapter<OUTPUT extends Container> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Container> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Container> {

		public static class Adapter extends BuilderBase.Adapter.Default<Container> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Container.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
}