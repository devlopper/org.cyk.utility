package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.data.Form;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MyEntityEditPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__() {
		return super.__getWindowBuilder__().setTitleValue("Création/Mise à jour/Suppression de catégorie de fonction");
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		MyEntityEditForm form = __inject__(MyEntityEditForm.class);
		form.setData(__inject__(MyEntityData.class));
		form.setTitle("Création/Mise à jour/Suppression de catégorie de fonction");
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilderByObjectByFieldNames(form, Form.PROPERTY_TITLE).addRoles(ComponentRole.TITLE);
	
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntityData.PROPERTY_CODE);
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntityData.PROPERTY_NAME);
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), MyEntityData.PROPERTY_DESCRIPTION);
		
		viewBuilder.addComponentBuilderByObjectByMethodName(form, Form.METHOD_SUBMIT);
		
		return viewBuilder;
	}
	
}
