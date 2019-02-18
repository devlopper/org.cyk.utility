package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.window.WindowRenderType;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.internationalization.InternalizationKeyStringType;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.string.Case;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCustom;

public class CommandableBuilderImpl extends AbstractVisibleComponentBuilderImpl<Commandable> implements CommandableBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandBuilder command;
	private String name;
	private CommandableRenderType renderType;
	private NavigationBuilder navigation;
	private Class<? extends WindowRenderType> windowRenderTypeClass;
	private Icon icon;
	
	@Override
	protected void __execute__(Commandable commandable) {
		super.__execute__(commandable);
		String name = null;
		
		CommandBuilder command = getCommand();
		if(command!=null)
			commandable.setCommand(command.execute().getOutput());
		
		CommandableRenderType renderType = getRenderType();
		if(renderType == null)
			renderType = __inject__(CommandableRenderTypeButton.class);
		commandable.setRenderType(renderType);
		
		NavigationBuilder navigation = getNavigation();
		if(navigation!=null) {
			navigation.setPropertyIfNull(Properties.CONTEXT, getContext());
			navigation.setPropertyIfNull(Properties.UNIFORM_RESOURCE_LOCATOR_MAP, getUniformResourceLocatorMap());
			try {
				commandable.setNavigation(navigation.execute().getOutput());
			} catch (Exception exception) {
				exception.printStackTrace();
				name = exception.getMessage();
			}
		}
		
		if(__injectStringHelper__().isBlank(name))
			name = getName();
		
		if(__injectStringHelper__().isBlank(name)) {
			InternalizationStringBuilder nameInternalization = getNameInternalization();
			if(nameInternalization==null) {
				SystemAction systemAction = null;
				if(systemAction == null && command!=null)
					systemAction = command.getFunction().getAction();
				if(systemAction == null && navigation!=null)
					systemAction = navigation.getSystemAction();
				if(systemAction == null && navigation.getIdentifierBuilder()!=null)
					systemAction = navigation.getIdentifierBuilder().getSystemAction();
				
				if(systemAction!=null)
					name = __inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setCase(Case.FIRST_CHARACTER_UPPER).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput();
				
				/*
				if(navigation!=null) {
					SystemAction systemAction = navigation.getSystemAction();
					if(systemAction == null && navigation.getIdentifierBuilder()!=null)
						systemAction = navigation.getIdentifierBuilder().getSystemAction();
					
					if(systemAction!=null) {
						name = __inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setCase(Case.FIRST_CHARACTER_UPPER).setKeyType(InternalizationKeyStringType.VERB).execute().getOutput();
					}
				}	
				*/
			}else
				name = nameInternalization.execute().getOutput();
			
		}else {
			
		}
		commandable.setName(name);
	}
	
	@Override
	public CommandableBuilder addRoles(ComponentRole... roles) {
		return (CommandableBuilder) super.addRoles(roles);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public CommandableBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public CommandableBuilder setNameInternalization(InternalizationStringBuilder nameInternalization) {
		return (CommandableBuilder) super.setNameInternalization(nameInternalization);
	}
	
	@Override
	public CommandableBuilder setNameInternalizationKeyValue(String nameInternalizationKeyValue) {
		return (CommandableBuilder) super.setNameInternalizationKeyValue(nameInternalizationKeyValue);
	}
	
	@Override
	public Icon getIcon() {
		return icon;
	}
	@Override
	public CommandableBuilder setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}
	
	@Override
	public CommandableRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public CommandableBuilder setRenderType(CommandableRenderType renderType) {
		this.renderType = renderType;
		return this;
	}
	
	@Override
	public CommandBuilder getCommand() {
		return command;
	}
	
	@Override
	public CommandBuilder getCommand(Boolean injectIfNull) {
		return (CommandBuilder) __getInjectIfNull__(FIELD_COMMAND, injectIfNull);
	}
	
	@Override
	public CommandableBuilder setCommand(CommandBuilder command) {
		this.command = command;
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnable(Collection<Runnable> runnables) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(runnables);
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnable(Runnable...runnables) {
		return addCommandFunctionTryRunRunnable(__injectCollectionHelper__().instanciate(runnables));
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
		__inject__(CollectionHelper.class).addElementAt(getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).getRunnables(Boolean.TRUE), index, runnable);
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
	
	@Override
	public NavigationBuilder getNavigation() {
		return navigation;
	}
	
	@Override
	public CommandableBuilder injectNavigationIfNull() {
		getNavigation(Boolean.TRUE);
		return this;
	}
	
	@Override
	public NavigationBuilder getNavigation(Boolean injectIfNull) {
		return (NavigationBuilder) __getInjectIfNull__(FIELD_NAVIGATION, injectIfNull);
	}
	
	@Override
	public CommandableBuilder setNavigation(NavigationBuilder navigation) {
		this.navigation = navigation;
		return this;
	}
	
	@Override
	public CommandableBuilder setNavigationIdentifier(Object identifier) {
		getNavigation(Boolean.TRUE).setIdentifier(identifier);
		return this;
	}
	
	@Override
	public CommandableBuilder setNavigationParameters(Object... keyValues) {
		getNavigation(Boolean.TRUE).setParameters(keyValues);
		return this;
	}
	
	@Override
	public CommandableBuilder setNavigationIdentifierAndParameters(Object identifier, Object[] keyValues) {
		setNavigationIdentifier(identifier);
		setNavigationParameters(keyValues);
		return this;
	}
	
	@Override
	public CommandableBuilder addNavigationDynamicParameterNames(Object... names) {
		getNavigation(Boolean.TRUE).getDynamicParameterNames(Boolean.TRUE).add(names);
		return this;
	}
	
	@Override
	public CommandableBuilder setNavigationSystemAction(SystemAction systemAction) {
		getNavigation(Boolean.TRUE).setSystemAction(systemAction);
		return this;
	}
	
	@Override
	public CommandableBuilder setNavigationIdentifierBuilderSystemAction(SystemAction systemAction) {
		getNavigation(Boolean.TRUE).setIdentifierBuilderSystemAction(systemAction);
		return this;
	}
	
	@Override
	public Class<? extends WindowRenderType> getWindowRenderTypeClass() {
		return windowRenderTypeClass;
	}
	
	@Override
	public CommandableBuilder setWindowRenderTypeClass(Class<? extends WindowRenderType> windowRenderTypeClass) {
		this.windowRenderTypeClass = windowRenderTypeClass;
		return this;
	}
	
	/**/
	
	public static final String FIELD_COMMAND = "command";
	public static final String FIELD_NAVIGATION = "navigation";
}
