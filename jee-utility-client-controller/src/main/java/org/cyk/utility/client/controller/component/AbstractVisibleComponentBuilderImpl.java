package org.cyk.utility.client.controller.component;

public abstract class AbstractVisibleComponentBuilderImpl<COMPONENT extends VisibleComponent> extends AbstractComponentBuilderImpl<COMPONENT> implements VisibleComponentBuilder<COMPONENT> {
	private static final long serialVersionUID = 1L;

	private VisibleComponentArea area;
	
	@Override
	protected void __execute__(COMPONENT component) {
		super.__execute__(component);
		VisibleComponentArea area = getArea();
		if(area!=null) {
			component.setArea(area);
		}
	}
	
	@Override
	public VisibleComponentArea getArea() {
		return area;
	}

	@Override
	public VisibleComponentArea getArea(Boolean injectIfNull) {
		return (VisibleComponentArea) __getInjectIfNull__(FIELD_AREA, injectIfNull);
	}

	@Override
	public VisibleComponentBuilder<COMPONENT> setArea(VisibleComponentArea area) {
		this.area = area;
		return this;
	}

	public static final String FIELD_AREA = "area";
}
