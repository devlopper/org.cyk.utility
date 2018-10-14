package org.cyk.utility.client.controller.component.layout;

import java.util.Map;

import org.cyk.utility.client.controller.screen.CollectionInstanceScreen;
import org.cyk.utility.client.controller.screen.Screen;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface LayoutItemCssClassBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	Integer getMaximumColumnCount();
	LayoutItemCssClassBuilder setMaximumColumnCount(Integer maximumColumnCount);
	
	CollectionInstanceScreen getScreens();
	CollectionInstanceScreen getScreens(Boolean injectIfNull);
	LayoutItemCssClassBuilder setScreens(CollectionInstanceScreen screens);
	
	Map<Screen,Integer> getColumnCountMap();
	Map<Screen,Integer> getColumnCountMap(Boolean createIfNull);
	LayoutItemCssClassBuilder setColumnMap(Map<Screen,Integer> columnCountMap);
	
	LayoutItemCssClassBuilder addScreen(Screen screen,Integer columnCount);
	LayoutItemCssClassBuilder addScreen(Screen screen);
}
