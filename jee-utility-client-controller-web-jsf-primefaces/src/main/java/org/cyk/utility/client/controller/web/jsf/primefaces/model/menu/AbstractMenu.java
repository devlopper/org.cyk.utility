package org.cyk.utility.client.controller.web.jsf.primefaces.model.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractMenu extends AbstractObject implements Serializable {

	protected Collection<MenuItem> items;
	
	public Collection<MenuItem> getItems(Boolean injectIfNull) {
		if(items == null && Boolean.TRUE.equals(injectIfNull))
			items = new ArrayList<>();
		return items;
	}
	
	public AbstractMenu addItems(Collection<MenuItem> items) {
		if(CollectionHelper.isEmpty(items))
			return this;
		getItems(Boolean.TRUE).addAll(items);
		return this;
	}
	
	public AbstractMenu addItems(MenuItem...items) {
		if(ArrayHelper.isEmpty(items))
			return this;
		return addItems(CollectionHelper.listOf(items));
	}
	
	public AbstractMenu addItemsByArguments(Collection<Map<Object,Object>> itemsArguments) {
		if(CollectionHelper.isEmpty(itemsArguments))
			return this;
		for(Map<Object,Object> map : itemsArguments)
			addItems(MenuItem.build(map));
		return this;
	}
	
	public AbstractMenu addItemsByArguments(@SuppressWarnings("unchecked") Map<Object,Object>...itemsArguments) {
		if(ArrayHelper.isEmpty(itemsArguments))
			return this;
		return addItemsByArguments(CollectionHelper.listOf(itemsArguments));
	}
	
	@SuppressWarnings("unchecked")
	public AbstractMenu addItemByArguments(Object...itemArguments) {
		Map<Object,Object> map = ArrayHelper.isEmpty(itemArguments) ? new HashMap<>() : MapHelper.instantiate(itemArguments);
		return addItemsByArguments(map);
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<MENU extends AbstractMenu> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<MENU> implements Serializable {

	}
	
}
