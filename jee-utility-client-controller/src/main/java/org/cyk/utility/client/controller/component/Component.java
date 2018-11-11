package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.object.Objects;

public interface Component extends Objectable {

	LayoutItem getLayoutItem();
	Component setLayoutItem(LayoutItem layoutItem);
	
	ComponentRoles getRoles();
	ComponentRoles getRoles(Boolean injectIfNull);
	Component setRoles(ComponentRoles roles);
	
	Objects getUpdatables();
	Objects getUpdatables(Boolean injectIfNull);
	Component setUpdatables(Objects updatables);
	
	Object getTargetModel();
	void setTargetModel(Object targetModel);//void because of java bean issue
	
	Boolean getIsTargetModelBuilt();
	Component setIsTargetModelBuilt(Boolean isTargetModelBuilt);
}
