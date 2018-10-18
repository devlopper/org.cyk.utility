package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.device.Device;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class LayoutBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Layout> implements LayoutBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilerItems items;
	private Type type;
	
	@Override
	protected Layout __execute__() throws Exception {
		Layout layout = __inject__(Layout.class);
		LayoutBuilerItems items = getItems();
		if(items == null) {
			Type type = getType();
			if(Type.FORM.equals(type)) {
				items = getItems(Boolean.TRUE);
				Integer maximumWidth = __inject__(LayoutWidthGetter.class).execute().getOutput().intValue();
				//Header
				items.add(__inject__(LayoutBuilerItem.class).setWidth(maximumWidth).setWidthForOther(maximumWidth).setWidthForDesktop(maximumWidth)
						.setWidthForTablet(maximumWidth).setWidthForPhone(maximumWidth));
				//Body
				items.add(__inject__(LayoutBuilerItem.class).setWidth(maximumWidth).setWidthForOther(maximumWidth).setWidthForDesktop(maximumWidth)
						.setWidthForTablet(maximumWidth).setWidthForPhone(maximumWidth));
				//Footer
				items.add(__inject__(LayoutBuilerItem.class).setWidth(maximumWidth).setWidthForOther(maximumWidth).setWidthForDesktop(maximumWidth)
						.setWidthForTablet(maximumWidth).setWidthForPhone(maximumWidth));
			}
		}
		if(items!=null) {
			Collection<LayoutBuilerItem> collection = items.get();
			if(collection!=null) {
				//Collection<Class<? extends Device>> deviceClasses = Arrays.asList(null,DeviceTelevision.class,DeviceDesktop.class,DeviceTablet.class,DevicePhone.class);
				//Map<Class<? extends Device>,Integer> totals = new HashMap<>();
				//for(Class<? extends Device> index : deviceClasses)
				//	totals.put(index, 0);
				
				for(LayoutBuilerItem index : collection) {
					Collection<Object> deviceClassesAndWidths = new ArrayList<>();
					if(index.getWidthMap()!=null) {
						for(Map.Entry<Class<? extends Device>, Integer> entry : index.getWidthMap().entrySet()) {
							deviceClassesAndWidths.add(entry.getKey());
							Integer width = entry.getValue();
							if(width == null)
								width =  __inject__(LayoutWidthGetter.class).setDeviceClass(entry.getKey()).execute().getOutput().intValue();
							
							/*//Integer total = totals.get(entry.getKey());
							if(width == null) {
								//width = getMaximumWidth() - total;
								//totals.put(entry.getKey(), 0);
							}else {
								//totals.put(entry.getKey(), total+width);
							}*/
							deviceClassesAndWidths.add(width);
						}
					}
					layout.addItemFromDeviceClassAndWidths(deviceClassesAndWidths.toArray());
				}
			}
		}
		return layout;
	}
	
	@Override
	public LayoutBuilerItems getItems() {
		return items;
	}

	@Override
	public LayoutBuilerItems getItems(Boolean injectIfNull) {
		LayoutBuilerItems items = getItems();
		if(items == null && Boolean.TRUE.equals(injectIfNull))
			setItems(items = __inject__(LayoutBuilerItems.class));
		return items;
	}

	@Override
	public LayoutBuilder setItems(LayoutBuilerItems items) {
		this.items = items;
		return this;
	}

	@Override
	public LayoutBuilder addItems(Collection<LayoutBuilerItem> items) {
		if(__injectCollectionHelper__().isNotEmpty(items))
			getItems(Boolean.TRUE).add(items);
		return this;
	}
	
	@Override
	public LayoutBuilder addItems(LayoutBuilerItem... items) {
		return addItems(__injectCollectionHelper__().instanciate(items));
	}

	@Override
	public LayoutBuilder setType(Type type) {
		this.type = type;
		return this;
	}
	
	@Override
	public Type getType() {
		return type;
	}
}
