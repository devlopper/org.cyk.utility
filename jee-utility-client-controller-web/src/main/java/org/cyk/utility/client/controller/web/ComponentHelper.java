package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeButton;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeLink;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.input.InputStringLineMany;
import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnPanel;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;

@Singleton @Named
public class ComponentHelper extends AbstractSingleton implements Serializable {
	private static final long serialVersionUID = 1L;

	public RandomHelper getRandomHelper() {
		return __inject__(RandomHelper.class);
	}
	
	public Boolean isInputStringLineOne(Object object) {
		return object instanceof InputStringLineOne;
	}
	
	public Boolean isInputStringLineMany(Object object) {
		return object instanceof InputStringLineMany;
	}
	
	public Boolean isInputChoiceOneCombo(Object object) {
		return object instanceof InputChoiceOneCombo;
	}
	
	public Boolean isOutputStringText(Object object) {
		return object instanceof OutputStringText;
	}
	
	public Boolean isOutputStringLabel(Object object) {
		return object instanceof OutputStringLabel;
	}
	
	public Boolean isOutputStringMessage(Object object) {
		return object instanceof OutputStringMessage;
	}
	
	public Boolean isLayout(Object object) {
		return object instanceof Layout;
	}
	
	public Boolean isLayoutItem(Object object) {
		return object instanceof LayoutItem;
	}
	
	public Boolean isView(Object object) {
		return object instanceof View;
	}
	
	public Boolean isInsert(Object object) {
		return object instanceof Insert;
	}
	
	public Boolean isMenu(Object object) {
		return object instanceof Menu;
	}
	
	public Boolean isMenuRenderTypeColumnPanel(Object object) {
		return object instanceof MenuRenderTypeColumnPanel;
	}
	
	public Boolean isMenuRenderTypeRowBar(Object object) {
		return object instanceof MenuRenderTypeRowBar;
	}
	
	public Boolean isGrid(Object object) {
		return object instanceof Grid;
	}
	
	public Boolean isCell(Object object) {
		return object instanceof Cell;
	}
	
	public Boolean isCommandable(Object object) {
		return object instanceof Commandable;
	}
	
	public Boolean isCommandableRenderTypeButton(Object object) {
		return object instanceof CommandableRenderTypeButton;
	}
	
	public Boolean isCommandableRenderTypeLink(Object object) {
		return object instanceof CommandableRenderTypeLink;
	}
	
	public Class<?> getSystemActionCreateClass(){
		return SystemActionCreate.class;
	}
	
	public Class<?> getSystemActionReadClass(){
		return SystemActionRead.class;
	}
	
	public Class<?> getSystemActionUpdateClass(){
		return SystemActionUpdate.class;
	}
	
	public Class<?> getSystemActionDeleteClass(){
		return SystemActionDelete.class;
	}
	
	public Class<?> getSystemActionSelectClass(){
		return SystemActionSelect.class;
	}
	
	public Class<?> getSystemActionProcessClass(){
		return SystemActionProcess.class;
	}
	
	/**/
	
	public String getGlobalFormComponentIdentifier() {
		return GLOBAL_FORM_IDENTIFIER;
	}
	
	public String getGlobalMessagesOwnerInlineComponentIdentifier() {
		return GLOBAL_MESSAGES_OWNER_INLINE_IDENTIFIER;
	}
	
	public String getGlobalMessagesOwnerGrowlComponentIdentifier() {
		return GLOBAL_MESSAGES_OWNER_GROWL_IDENTIFIER;
	}
	
	public String getGlobalMessagesOwnerDialogComponentIdentifier() {
		return GLOBAL_MESSAGES_OWNER_DIALOG_IDENTIFIER;
	}
	
	public String getGlobalMessagesTargetInlineComponentIdentifier() {
		return GLOBAL_MESSAGES_TARGET_INLINE_IDENTIFIER;
	}
	
	public String getGlobalMessagesTargetGrowlComponentIdentifier() {
		return GLOBAL_MESSAGES_TARGET_GROWL_IDENTIFIER;
	}
	
	public String getGlobalMessagesTargetDialogComponentIdentifier() {
		return GLOBAL_MESSAGES_TARGET_DIALOG_IDENTIFIER;
	}
	
	public String getGlobalMessagesOwnerInlineComponentClientIdentifier() {
		return GLOBAL_MESSAGES_OWNER_INLINE_CLIENT_IDENTIFIER;
	}
	
	public String getGlobalMessagesOwnerGrowlComponentClientIdentifier() {
		return GLOBAL_MESSAGES_OWNER_GROWL_CLIENT_IDENTIFIER;
	}
	
	public String getGlobalMessagesOwnerDialogComponentClientIdentifier() {
		return GLOBAL_MESSAGES_OWNER_DIALOG_CLIENT_IDENTIFIER;
	}
	
	public String getGlobalMessagesTargetInlineComponentClientIdentifier() {
		return GLOBAL_MESSAGES_TARGET_INLINE_CLIENT_IDENTIFIER;
	}
	
	public String getGlobalMessagesTargetGrowlComponentClientIdentifier() {
		return GLOBAL_MESSAGES_TARGET_GROWL_CLIENT_IDENTIFIER;
	}
	
	public String getGlobalMessagesTargetDialogComponentClientIdentifier() {
		return GLOBAL_MESSAGES_TARGET_DIALOG_CLIENT_IDENTIFIER;
	}
	
	/**/
	
	public static final String GLOBAL_FORM_IDENTIFIER = "form";
	
	public static final String GLOBAL_MESSAGES_OWNER_INLINE_IDENTIFIER = "globalMessagesOwnerInline";
	public static final String GLOBAL_MESSAGES_OWNER_GROWL_IDENTIFIER = "globalMessagesOwnerGrowl";
	public static final String GLOBAL_MESSAGES_OWNER_DIALOG_IDENTIFIER = "globalMessagesOwnerDialog";
	
	public static final String GLOBAL_MESSAGES_TARGET_INLINE_IDENTIFIER = "globalMessagesTargetInline";
	public static final String GLOBAL_MESSAGES_TARGET_GROWL_IDENTIFIER = "globalMessagesTargetGrowl";
	public static final String GLOBAL_MESSAGES_TARGET_DIALOG_IDENTIFIER = "globalMessagesTargetDialog";
	
	public static final String GLOBAL_MESSAGES_OWNER_INLINE_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+CharacterConstant.COLON+GLOBAL_MESSAGES_OWNER_INLINE_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_OWNER_GROWL_CLIENT_IDENTIFIER = GLOBAL_MESSAGES_OWNER_GROWL_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_OWNER_DIALOG_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+CharacterConstant.COLON+GLOBAL_MESSAGES_OWNER_DIALOG_IDENTIFIER;
	
	public static final String GLOBAL_MESSAGES_TARGET_INLINE_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+CharacterConstant.COLON+GLOBAL_MESSAGES_TARGET_INLINE_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_TARGET_GROWL_CLIENT_IDENTIFIER = GLOBAL_MESSAGES_TARGET_GROWL_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_TARGET_DIALOG_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+CharacterConstant.COLON+GLOBAL_MESSAGES_TARGET_DIALOG_IDENTIFIER;
	
}
