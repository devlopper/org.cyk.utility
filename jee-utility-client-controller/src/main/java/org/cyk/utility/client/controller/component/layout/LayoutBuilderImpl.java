package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public class LayoutBuilderImpl extends AbstractVisibleComponentBuilderImpl<Layout> implements LayoutBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItemBuilders items;
	private Type type;
	
	@Override
	protected void __execute__(Layout layout) {
		LayoutItemBuilders items = getItems();
		if(__injectCollectionHelper__().isNotEmpty(items)) {
			for(LayoutItemBuilder index : items.get()) {
				/*Collection<Object> deviceClassesAndWidths = new ArrayList<>();
				if(index.getWidthMap()!=null) {
					for(Map.Entry<Class<? extends Device>, Integer> entry : index.getWidthMap().entrySet()) {
						deviceClassesAndWidths.add(entry.getKey());
						Integer width = entry.getValue();
						if(width == null)
							width =  __inject__(LayoutWidthGetter.class).setDeviceClass(entry.getKey()).execute().getOutput().intValue();
						deviceClassesAndWidths.add(width);
					}
				}
				layout.addItemFromDeviceClassAndWidths(deviceClassesAndWidths.toArray());
				*/
				LayoutItem layoutItem = index.execute().getOutput();
				if(layoutItem!=null)
					layout.addChild(layoutItem);			
			}
		}
	}
	
	@Override
	public LayoutItemBuilders getItems() {
		return items;
	}

	@Override
	public LayoutItemBuilders getItems(Boolean injectIfNull) {
		return (LayoutItemBuilders) __getInjectIfNull__(FIELD_ITEMS, injectIfNull);
	}

	@Override
	public LayoutBuilder setItems(LayoutItemBuilders items) {
		this.items = items;
		return this;
	}

	@Override
	public LayoutBuilder addItems(Collection<LayoutItemBuilder> items) {
		if(__injectCollectionHelper__().isNotEmpty(items))
			getItems(Boolean.TRUE).add(items);
		return this;
	}
	
	@Override
	public LayoutBuilder addItems(LayoutItemBuilder... items) {
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
	
	/**/
	
	public static final String FIELD_ITEMS = "items";
}
