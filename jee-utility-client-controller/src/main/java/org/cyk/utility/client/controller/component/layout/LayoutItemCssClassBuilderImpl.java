package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.client.controller.screen.CollectionInstanceScreen;
import org.cyk.utility.client.controller.screen.Screen;
import org.cyk.utility.collection.CollectionInstanceString;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public class LayoutItemCssClassBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements LayoutItemCssClassBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Integer maximumColumnCount;
	private CollectionInstanceScreen screens;
	private Map<Screen, Integer> columnCountMap;
	
	@Override
	protected String __execute__() throws Exception {
		CollectionInstanceString g;
		return super.__execute__();
	}
	
	@Override
	public Integer getMaximumColumnCount() {
		return maximumColumnCount;
	}

	@Override
	public LayoutItemCssClassBuilder setMaximumColumnCount(Integer maximumColumnCount) {
		this.maximumColumnCount = maximumColumnCount;
		return this;
	}

	@Override
	public CollectionInstanceScreen getScreens() {
		return screens;
	}

	@Override
	public CollectionInstanceScreen getScreens(Boolean injectIfNull) {
		CollectionInstanceScreen screens = getScreens();
		if(screens == null && Boolean.TRUE.equals(injectIfNull))
			setScreens(screens = __inject__(CollectionInstanceScreen.class));
		return screens;
	}

	@Override
	public LayoutItemCssClassBuilder setScreens(CollectionInstanceScreen screens) {
		this.screens = screens;
		return this;
	}

	@Override
	public Map<Screen, Integer> getColumnCountMap() {
		return columnCountMap;
	}

	@Override
	public Map<Screen, Integer> getColumnCountMap(Boolean createIfNull) {
		Map<Screen, Integer> map = getColumnCountMap();
		if(map == null && Boolean.TRUE.equals(createIfNull))
			setColumnMap(map = new LinkedHashMap<>());
		return map;
	}

	@Override
	public LayoutItemCssClassBuilder setColumnMap(Map<Screen, Integer> columnCountMap) {
		this.columnCountMap = columnCountMap;
		return this;
	}

	@Override
	public LayoutItemCssClassBuilder addScreen(Screen screen, Integer columnCount) {
		if(screen!=null && columnCount!=null) {
			getColumnCountMap(Boolean.TRUE).put(screen, columnCount);
		}
		return this;
	}

	@Override
	public LayoutItemCssClassBuilder addScreen(Screen screen) {
		return addScreen(screen, getMaximumColumnCount());
	}

}
