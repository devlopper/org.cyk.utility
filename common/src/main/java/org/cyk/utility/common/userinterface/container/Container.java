package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;

import org.cyk.utility.common.Properties;
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
	
	@Override
	public Container _setPropertyTitle(Object title){
		super._setPropertyTitle(title);
		getPropertiesMap().setIfNull(Properties.CONTENT_TITLE, getPropertiesMap().getTitle());
		return this;
	}
	
	@Override
	public Container _setPropertyContentTitle(Object contentTitle){
		super._setPropertyContentTitle(contentTitle);
		getPropertiesMap().setIfNull(Properties.TITLE, getPropertiesMap().getContentTitle());
		return this;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Container> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Container> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
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