package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.model.Area;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Component extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	protected CollectionHelper.Instance<Component> children;
	
	/**/

	public Component addOneChild(Component component){
		if(children == null)
			children = instanciateChildrenCollection();
		children.addOne(component);
		return this;
	}
	
	public Component addManyChild(Collection<Component> components){
		if(CollectionHelper.getInstance().isNotEmpty(components))
			for(Component component : components)
				addOneChild(component);
		return this;
	}
	
	public Component addManyChild(Component...components){
		if(ArrayHelper.getInstance().isNotEmpty(components))
			addManyChild(Arrays.asList(components));
		return this;
	}
	
	protected CollectionHelper.Instance<Component> instanciateChildrenCollection(){
		CollectionHelper.Instance<Component> collection = new CollectionHelper.Instance<Component>();
		collection.setElementClass(Component.class);
		return collection;
	}
		
	/**/
	
	public static interface BuilderBase<OUTPUT extends Component> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {

		public static class Adapter<OUTPUT extends Component> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Component> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Component> {

		public static class Adapter extends BuilderBase.Adapter.Default<Component> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Component.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Visible extends Component implements Serializable {
		private static final long serialVersionUID = 1L;

		private Area area = new Area();
		
		/**/

		public Visible setLength(Number length){
			getArea().getLength().setDistance(length);
			return this;
		}
		
		public Visible setWidth(Number width){
			getArea().getWidth().setDistance(width);
			return this;
		}
		
		/**/
		
		public static interface BuilderBase<OUTPUT extends Visible> extends Component.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Visible> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Visible> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Visible> {

			public static class Adapter extends BuilderBase.Adapter.Default<Visible> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Visible.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
		}
	
		/**/

	}
	
	public static class Invisible extends Component implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/

		public static interface BuilderBase<OUTPUT extends Invisible> extends Component.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Invisible> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Invisible> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Invisible> {

			public static class Adapter extends BuilderBase.Adapter.Default<Invisible> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Invisible.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
		}
	}
}