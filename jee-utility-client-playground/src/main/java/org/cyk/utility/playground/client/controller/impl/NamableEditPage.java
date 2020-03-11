package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityEditPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.api.NamableController;
import org.cyk.utility.playground.client.controller.entities.Namable;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NamableEditPage extends AbstractEntityEditPageContainerManagedImpl<Namable> implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	private CommandButton recordCommandButton;
	private String valuesSessionIdentifier;
	private Boolean isCollectionable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		isCollectionable = ValueHelper.convertToBoolean(Faces.getRequestParameter(ParameterName.MULTIPLE.getValue()));
		if(Boolean.TRUE.equals(isCollectionable)) {
			form = null;
			
			Collection<Namable> namables = null;
			if(StringHelper.isBlank(valuesSessionIdentifier = Faces.getRequestParameter("entityidentifier"))) {
				String sessionIdentifier = Faces.getRequestParameter(ParameterName.SESSION_IDENTIFIER.getValue());
				if(StringHelper.isNotBlank(sessionIdentifier))
					namables = (Collection<Namable>) SessionHelper.getAttributeValue(sessionIdentifier);
			}else {
				Namable activity = __inject__(NamableController.class).readBySystemIdentifier(Faces.getRequestParameter("entityidentifier"));
				if(activity != null)
					namables = CollectionHelper.listOf(activity);
			}
			
			dataTable = DataTable.build(DataTable.FIELD_ELEMENT_CLASS,Namable.class,DataTable.FIELD_VALUE,namables);
			
			dataTable.addColumnsAfterRowIndex(
					Column.build(Column.FIELD_FIELD_NAME,Namable.FIELD_CODE)
					,Column.build(Column.FIELD_FIELD_NAME,Namable.FIELD_NAME)
					);	
			
			if(!Action.READ.equals(action)) {
				if(CollectionHelper.isNotEmpty(namables))
					namables.forEach(new Consumer<Namable>() {
						@Override
						public void accept(Namable namable) {
							namable.set__code__(namable.getCode());
							namable.set__name__(namable.getName());
						}
					});
				
				recordCommandButton = CommandButton.build(CommandButton.ConfiguratorImpl.FIELD_OBJECT,this,CommandButton.ConfiguratorImpl.FIELD_METHOD_NAME,"record"
						,CommandButton.FIELD_ICON,"fa fa-floppy-o");	
			}			
		}else {
			
		}
		
	}
	
	public String record() {
		@SuppressWarnings("unchecked")
		Collection<Namable> namables = (Collection<Namable>) dataTable.getValue();
		if(CollectionHelper.isEmpty(namables))
			return null;
		
		if(CollectionHelper.isNotEmpty(namables))
			namables.forEach(new Consumer<Namable>() {
				@Override
				public void accept(Namable namable) {
					namable.setCode(namable.get__code__());
					namable.setName(namable.get__name__());
				}
			});
		
		__inject__(NamableController.class).updateMany(namables,new Properties().setFields(Namable.FIELD_CODE+","+Namable.FIELD_NAME));
		namables.clear();
		namables = null;
		SessionHelper.setAttributeValue(valuesSessionIdentifier, null);
		return "Namables has been updated";
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		if(Boolean.TRUE.equals(isCollectionable))
			return "Modification des namables";
		return super.__getWindowTitleValue__();
	}
	
}
