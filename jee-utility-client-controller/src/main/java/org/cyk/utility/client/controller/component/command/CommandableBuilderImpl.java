package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCustom;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.ComponentRoles;
import org.cyk.utility.client.controller.component.window.WindowRenderType;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.icon.Icon;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class CommandableBuilderImpl extends AbstractVisibleComponentBuilderImpl<Commandable> implements CommandableBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandBuilder command;
	private String name;
	private CommandableRenderType renderType;
	private Class<? extends WindowRenderType> windowRenderTypeClass;
	private Icon icon;
	private SystemAction systemAction;
	private UniformResourceIdentifierAsFunctionParameter uniformResourceIdentifier;
	
	@Override
	protected void __execute__(Commandable commandable) {
		super.__execute__(commandable);
		String name = getName();
		
		CommandBuilder command = getCommand();
		if(command!=null)
			commandable.setCommand(command.execute().getOutput());
		
		SystemAction systemAction = getSystemAction();
		if(systemAction == null && command!=null && command.getFunction() != null)
			systemAction = command.getFunction().getAction();
		commandable.setSystemAction(systemAction);
		
		CommandableRenderType renderType = getRenderType();
		if(renderType == null)
			renderType = __inject__(CommandableRenderTypeButton.class);
		commandable.setRenderType(renderType);
		
		UniformResourceIdentifierAsFunctionParameter uniformResourceIdentifierAsFunctionParameter = getUniformResourceIdentifier();
		if(uniformResourceIdentifierAsFunctionParameter != null) {
			uniformResourceIdentifierAsFunctionParameter.setSystemAction(systemAction);
			uniformResourceIdentifierAsFunctionParameter.setRequest(getRequest());
			commandable.setUniformResourceIdentifier(UniformResourceIdentifierHelper.build(uniformResourceIdentifierAsFunctionParameter));
		}
		String derivedName = null;
		if(StringHelper.isBlank(derivedName)) {
			InternationalizationString nameInternationalization = getNameInternationalization();
			if(nameInternationalization==null) {
				if(systemAction!=null)
					derivedName = InternationalizationHelper.buildString(InternationalizationHelper
							.buildKey(systemAction,InternationalizationKeyStringType.VERB), null, null, Case.FIRST_CHARACTER_UPPER);
			}else {
				InternationalizationHelper.processStrings(nameInternationalization);
				derivedName = nameInternationalization.getValue();
			}
			
		}else {
			
		}	
		
		if(StringHelper.isBlank(name) && Boolean.TRUE.equals(__getIsFieldNameDerivable__(PROPERTY_NAME))) {
			name = derivedName;
		}
		commandable.setName(name);
		
		Icon icon = getIcon();
		if(icon == null && Boolean.TRUE.equals(__getIsFieldNameDerivable__(PROPERTY_ICON))) {
			ComponentRoles roles = getRoles();
			if(CollectionHelper.isNotEmpty(roles)) {
				if(CollectionHelper.contains(roles, ComponentRole.CREATOR))
					icon = Icon.PLUS;
				else if(CollectionHelper.contains(roles, ComponentRole.READER))
					icon = Icon.EYE;
				else if(CollectionHelper.contains(roles, ComponentRole.MODIFIER))
					icon = Icon.EDIT;
				else if(CollectionHelper.contains(roles, ComponentRole.REMOVER))
					icon = Icon.REMOVE;
			}
		}
		commandable.setIcon(icon);
		
		Object tooltip = getTooltip();
		if(tooltip == null && Boolean.TRUE.equals(__getIsFieldNameDerivable__(PROPERTY_TOOLTIP))) {
			if(StringHelper.isBlank(commandable.getName()))
				tooltip = derivedName;
			else
				tooltip = commandable.getName();
		}
		commandable.setTooltip(tooltip);
	}
	
	@Override
	public UniformResourceIdentifierAsFunctionParameter getUniformResourceIdentifier(Boolean injectIfNull) {
		if(uniformResourceIdentifier == null && Boolean.TRUE.equals(injectIfNull))
			setUniformResourceIdentifier(uniformResourceIdentifier = new UniformResourceIdentifierAsFunctionParameter());
		return uniformResourceIdentifier;
	}
	
	@Override
	public CommandableBuilder addRoles(ComponentRole... roles) {
		return (CommandableBuilder) super.addRoles(roles);
	}
	
	@Override
	public CommandableBuilder setDerivableFieldNames(Object... values) {
		return (CommandableBuilder) super.setDerivableFieldNames(values);
	}
	
	@Override
	public CommandableBuilder setUniformResourceIdentifierSystemAction(SystemAction systemAction) {
		getUniformResourceIdentifier(Boolean.TRUE).setSystemAction(systemAction);
		if(this.systemAction == null)
			this.systemAction = systemAction;
		return this;
	}
	
	@Override
	public CommandableBuilder setUniformResourceIdentifierSystemActionClass(Class<? extends SystemAction> systemActionClass) {
		getUniformResourceIdentifier(Boolean.TRUE).setSystemActionClass(systemActionClass);
		return this;
	}
	
	@Override
	public CommandableBuilder setUniformResourceIdentifierSystemActionEntityClass(Class<?> systemActionEntityClass) {
		getUniformResourceIdentifier(Boolean.TRUE).setSystemActionEntityClass(systemActionEntityClass);
		return this;
	}
	
	@Override
	public CommandBuilder getCommand(Boolean injectIfNull) {
		if(command == null && Boolean.TRUE.equals(injectIfNull))
			command = __inject__(CommandBuilder.class);
		return command;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnable(Collection<Runnable> runnables) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).addTryRunRunnables(runnables);
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnable(Runnable...runnables) {
		return addCommandFunctionTryRunRunnable(CollectionHelper.listOf(runnables));
	}
	
	@Override
	public CommandableBuilder setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(systemActionClass));
		return this;
	}
	
	@Override
	public CommandableBuilder setCommandFunctionAction(SystemAction systemAction) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemAction);
		return this;
	}
	
	@Override
	public CommandableBuilder setCommandFunctionActionCustom(Object actionIdentifier) {
		setCommandFunctionAction(__inject__(SystemActionCustom.class).setIdentifier(actionIdentifier));
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnableAt(Runnable runnable, Integer index) {
		CollectionHelper.addElementAt(getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).getRunnables(Boolean.TRUE), index, runnable);
		return this;
	}
	
	@Override
	public CommandableBuilder setCommandFunction(CommandFunction function) {
		getCommand(Boolean.TRUE).setFunction(function);
		return this;
	}
	
	@Override
	public CommandableBuilder setCommandFunctionData(Data data) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getProperties().setData(data);
		return this;
	}	
}
