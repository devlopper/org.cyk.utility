package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CollectionHelper.Instance;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.model.Area;
import org.cyk.utility.common.model.Interval;
import org.cyk.utility.common.userinterface.container.Container;
import org.cyk.utility.common.userinterface.input.Input;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Layout extends Component.Invisible implements Serializable {
	private static final long serialVersionUID = 1L;

	private Area area = new Area();
	private Type type = Type.DEFAULT;
	
	private Integer currentAreaLengthFrom = 0;
	private Integer currentAreaLengthTo = 0;
	private Integer currentAreaWidthFrom = 0;
	
	/**/

	public Layout() {
		super();
		children = instanciateChildrenCollection();
		children.addListener(new Adapter(this));
		getArea().getLength().setFrom(0).setTo(0);
		getArea().getWidth().setFrom(0).setTo(0);
	}
	
	@Override
	public Layout addOneChild(Component component) {
		super.addOneChild(component);
		return this;
	}
	
	@Override
	public Layout addManyChild(Component... components) {
		return (Layout) super.addManyChild(components);
	}
	
	@Override
	public Layout addManyChild(Collection<Component> components) {
		return (Layout) super.addManyChild(components);
	}
	
	public Layout end(){
		currentAreaLengthFrom = 0;
		currentAreaLengthTo = 0;
		normalize();
		currentAreaWidthFrom = currentAreaWidthFrom + 1;
		return this;
	}
	
	public java.util.Collection<Component> getWhereAreaWidthFromEqual(Integer areaWidthFrom){
		return CollectionHelper.getInstance().filter(getChildren().getElements(), FieldHelper.getInstance().buildPath(FIELD_AREA,Area.FIELD_WIDTH,Interval.FIELD_FROM)
				,areaWidthFrom);
	}
	
	public Visible getComponentFacing(Visible visible){
		for(Component element : getChildren().getElements())
			if(NumberHelper.getInstance().greatThanOrEqual(((Visible)element).getArea().getLength().getFrom(), visible.getArea().getLength().getTo()) 
					&& NumberHelper.getInstance().lessThanOrEqual(((Visible)element).getArea().getWidth().getFrom(), visible.getArea().getWidth().getFrom())
					&& NumberHelper.getInstance().greatThanOrEqual(((Visible)element).getArea().getWidth().getTo(), visible.getArea().getWidth().getTo())
					){
				return (Visible) element;
		}
		return null;
	}
	
	public Layout normalize(){
		Area area = getArea();
		for(Integer index = 0 ; index < area.getWidth().getDistance().intValue() ; index++){
			Visible visible = (Visible) CollectionHelper.getInstance().getLast(getWhereAreaWidthFromEqual(index));
			if(visible!=null){
				normalize(visible);
			}
		}
		return this;
	}
	
	public Layout normalize(Visible visible){
		Visible facing = getComponentFacing(visible);
		Number add = NumberHelper.getInstance().subtract(facing == null ? area.getLength().getTo() : facing.getArea().getLength().getFrom()
				, visible.getArea().getLength().getTo());
		visible.getArea().getLength().setTo(NumberHelper.getInstance().add(visible.getArea().getLength().getTo(),add));
		
		if(visible instanceof Input<?>){
			if( ((Input<?>)visible).getLabel()!=null ){
				((Input<?>)visible).getLabel().getArea().getWidth().setDistance(visible.getArea().getWidth().getDistance());
			}
		}
		return this;
	}
	
	public Integer countAreaWidthFrom(Integer rowIndex){
		return CollectionHelper.getInstance().getSize(CollectionHelper.getInstance().filter(getChildren().getElements(), FieldHelper.getInstance()
				.buildPath(FIELD_AREA,Area.FIELD_WIDTH,Interval.FIELD_FROM), rowIndex));
	}
	
	/**/
	
	public static class Adapter extends CollectionHelper.Instance.Listener.Adapter<Component> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Layout layout;
		
		public Adapter(Layout layout) {
			this.layout = layout;
		}
		
		@Override
		public void addOne(Instance<Component> instance, Component element, Object source,Object sourceObject) {
			Component.Visible visible = (Visible) element;
			Type type = InstanceHelper.getInstance().getIfNotNullElseDefault(layout.getType(),Type.DEFAULT == null ? Type.VERTICAL : Type.DEFAULT);
			if(Type.AUTO.equals(type))
				type = Type.VERTICAL;
			Integer areaLengthFrom = 0;
			Integer areaLengthTo = 0;
			Integer areaWidthFrom = 0;
			Integer elementCount = CollectionHelper.getInstance().getSize(instance.getElements());
			if(Type.VERTICAL.equals(type)){//one horizontal cell
				visible.getArea().getLength().setFrom(layout.getArea().getLength().getFrom()).setTo(1);//x
				visible.getArea().getWidth().setFrom(elementCount-1).setTo(elementCount);//y
			}else if(Type.HORIZONTAL.equals(type)){//one vertical cell
				visible.getArea().getLength().setFrom(elementCount-1).setTo(elementCount);//x
				visible.getArea().getWidth().setFrom(layout.getArea().getWidth().getFrom()).setTo(1);//y
			}else if(Type.ADAPTIVE.equals(type)){//
				areaLengthFrom = layout.getCurrentAreaLengthFrom();
				areaLengthTo = layout.getCurrentAreaLengthTo()+InstanceHelper.getInstance()
				.getIfNotNullElseDefault(visible.getArea().getLength().getDistance(),BigDecimal.ONE).intValue();
				areaWidthFrom = layout.getCurrentAreaWidthFrom();
				visible.getArea().getLength().setFrom(areaLengthFrom).setTo(areaLengthTo);//x
				Integer distance = visible.getArea().getWidth().getDistance() == null ? 1 : visible.getArea().getWidth().getDistance().intValue();
				visible.getArea().getWidth().setFrom(areaWidthFrom).setTo(areaWidthFrom+distance);//y
			}
			
			if(visible.getArea().getLength().getFrom().doubleValue() < layout.getArea().getLength().getFrom().doubleValue())
				layout.getArea().getLength().setFrom(visible.getArea().getLength().getFrom());
			if(visible.getArea().getLength().getTo().doubleValue() > layout.getArea().getLength().getTo().doubleValue())
				layout.getArea().getLength().setTo(visible.getArea().getLength().getTo());
			
			if(visible.getArea().getWidth().getFrom().doubleValue() < layout.getArea().getWidth().getFrom().doubleValue())
				layout.getArea().getWidth().setFrom(visible.getArea().getWidth().getFrom());
			if(visible.getArea().getWidth().getTo().doubleValue() > layout.getArea().getWidth().getTo().doubleValue())
				layout.getArea().getWidth().setTo(visible.getArea().getWidth().getTo());
			
			layout.setCurrentAreaLengthFrom( layout.getCurrentAreaLengthFrom() + visible.getArea().getLength().getDistance().intValue());
			layout.setCurrentAreaLengthTo( layout.getCurrentAreaLengthTo() + visible.getArea().getLength().getDistance().intValue());
			
		}
		
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
	
	public static enum Type {
		AUTO,VERTICAL,HORIZONTAL,ADAPTIVE,FREE
		;
		public static Type DEFAULT = VERTICAL;
	}

	/**/
	
	public static final String FIELD_AREA = "area";
}