package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.css.CascadeStyleSheetHelper;
import org.cyk.utility.css.Style;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.device.DeviceTablet;
import org.cyk.utility.device.DeviceTelevision;

public class LayoutItemBuilderImpl extends AbstractVisibleComponentBuilderImpl<LayoutItem> implements LayoutItemBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layout;
	
	@Override
	protected void __execute__(LayoutItem layoutItem) {
		super.__execute__(layoutItem);
		CascadeStyleSheetHelper cascadeStyleSheetHelper = __inject__(CascadeStyleSheetHelper.class);
		Style style = getStyle();
		DeviceScreenArea area = getArea();
		if(area == null) {
			area = __inject__(DeviceScreenArea.class);	
		}
		if(area!=null) {
			if(style == null)
				style = __inject__(Style.class);
			Map<Class<? extends Device>,Integer> map = area.getWidthProportions(Boolean.TRUE).getMap(Boolean.TRUE);
			for(Class<?> index : DEVICE_CLASSES) {
				Integer proportion = map.get(index == null ? null : index);
				String styleClass = cascadeStyleSheetHelper.buildStyleClassProportion(index == null ? Device.class : (Class<? extends Device>)index, proportion);
				style.getClasses(Boolean.TRUE).add(styleClass);
			}
		}
		setArea(area);
		if(style!=null)
			layoutItem.setStyle(style);
		LayoutBuilder layout = getLayout();
		if(layout!=null) {
			layoutItem.setLayout(layout.execute().getOutput());
		}
	}
	
	@Override
	public LayoutBuilder getLayout() {
		return layout;
	}

	@Override
	public LayoutItemBuilder setLayout(LayoutBuilder layout) {
		this.layout = layout;
		return this;
	}
	
	@Override
	public DeviceScreenDimensionProportions getAreaWidthProportions() {
		return getAreaWidthProportions(Boolean.FALSE);
	}
	
	@Override
	public DeviceScreenDimensionProportions getAreaWidthProportions(Boolean injectIfNull) {
		DeviceScreenArea area = getArea(injectIfNull);
		return area == null ? null : area.getWidthProportions(injectIfNull);
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsAllClasses(Integer _default, Integer television,Integer desktop, Integer tablet, Integer phone) {
		getAreaWidthProportions(Boolean.TRUE).setAllClasses(_default, television, desktop, tablet, phone);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsAll(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setAll(value);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsPhone(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setPhone(value);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsTablet(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setTablet(value);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsDesktop(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setDesktop(value);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsTelevision(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setTelevision(value);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsDefault(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setDefault(value);
		return this;
	}
	
	@Override
	public LayoutItemBuilder setAreaWidthProportionsNotPhone(Integer value) {
		getAreaWidthProportions(Boolean.TRUE).setAllClasses(value, value, value, value, null);
		return this;
	}
	
	@Override
	public LayoutItemBuilder addStyleClasses(String... classes) {
		return (LayoutItemBuilder) super.addStyleClasses(classes);
	}
	
	@Override
	public LayoutItemBuilder setOutputPropertyValue(Object value) {
		return (LayoutItemBuilder) super.setOutputPropertyValue(value);
	}
	
	@Override
	public LayoutItemBuilder setArea(DeviceScreenArea area) {
		return (LayoutItemBuilder) super.setArea(area);
	}
	
	@Override
	public LayoutItemBuilder addRoles(Collection<ComponentRole> roles) {
		return (LayoutItemBuilder) super.addRoles(roles);
	}
	
	@Override
	public LayoutItemBuilder addRoles(ComponentRole... roles) {
		return (LayoutItemBuilder) super.addRoles(roles);
	}
	
	/**/
	
	private static final Class<?>[] DEVICE_CLASSES = {null,DeviceTelevision.class,DeviceDesktop.class,DeviceTablet.class,DevicePhone.class};
	
}
