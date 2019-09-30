package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.primefaces.model.DualListModel;

import lombok.Getter;
import lombok.Setter;

public class PickList<T> extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private Class<T> klass;
	@Getter @Setter private DualListModel<T> model = new DualListModel<>();
	@Getter @Setter private BlockUI blockUI;
	
	@Getter @Setter private Collection<T> available,selected;
	
	public PickList(Class<T> klass) {
		this.klass = klass;
		blockUI = new BlockUI();
		blockUI.getProperties().setWidgetVar("pickListBlockUI");
		blockUI.getProperties().setBlock("pickList");
		blockUI.getProperties().setTrigger("initialisePickList");
		blockUI.getProperties().setBlocked(Boolean.TRUE);
	}
	
	@Override
	protected void __initialise__() {
		super.__initialise__();
		if(available == null)
			available = __inject__(Controller.class).read(klass,new Properties().setIsPageable(Boolean.FALSE));
		if(CollectionHelper.isNotEmpty(available))
			model = __inject__(PrimefacesHelper.class).buildDualList(available, selected);
	}
	
	public String getLabel(T record) {
		return record == null ? "?? NULL ??" : record.toString();
	}
}
