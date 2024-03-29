package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionProcess;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionSelect;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeButton;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeLink;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.input.InputBooleanButton;
import org.cyk.utility.client.controller.component.input.InputBooleanCheckBox;
import org.cyk.utility.client.controller.component.input.InputDate;
import org.cyk.utility.client.controller.component.input.InputDateTime;
import org.cyk.utility.client.controller.component.input.InputFile;
import org.cyk.utility.client.controller.component.input.InputNumber;
import org.cyk.utility.client.controller.component.input.InputStringEditor;
import org.cyk.utility.client.controller.component.input.InputStringLineMany;
import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyAutoComplete;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneAutoComplete;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneRadio;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.component.link.Link;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnContext;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnPanel;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.component.output.OutputFile;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringLink;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.text.Text;
import org.cyk.utility.client.controller.component.view.View;

@ApplicationScoped @Named
public class ComponentHelper extends AbstractSingleton implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String globalMessagesTargetsIdentifiers;
	
	public Boolean isInputStringEditor(Object object) {
		return object instanceof InputStringEditor;
	}
	
	public Boolean isInputStringLineOne(Object object) {
		return object instanceof InputStringLineOne;
	}
	
	public Boolean isInputStringLineMany(Object object) {
		return object instanceof InputStringLineMany;
	}
	
	public Boolean isInputBooleanCheckBox(Object object) {
		return object instanceof InputBooleanCheckBox;
	}
	
	public Boolean isInputBooleanButton(Object object) {
		return object instanceof InputBooleanButton;
	}
	
	public Boolean isInputChoiceOneCombo(Object object) {
		return object instanceof InputChoiceOneCombo;
	}
	
	public Boolean isInputChoiceOneRadio(Object object) {
		return object instanceof InputChoiceOneRadio;
	}
	
	public Boolean isInputChoiceOneAutoComplete(Object object) {
		return object instanceof InputChoiceOneAutoComplete;
	}
	
	public Boolean isInputChoiceManyCheckBox(Object object) {
		return object instanceof InputChoiceManyCheckBox;
	}
	
	public Boolean isInputChoiceManyAutoComplete(Object object) {
		return object instanceof InputChoiceManyAutoComplete;
	}
	
	public Boolean isInputFile(Object object) {
		return object instanceof InputFile;
	}
	
	public Boolean isInputDate(Object object) {
		return object instanceof InputDate;
	}
	
	public Boolean isInputDateTime(Object object) {
		return object instanceof InputDateTime;
	}
	
	public Boolean isInputNumber(Object object) {
		return object instanceof InputNumber;
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
	
	public Boolean isOutputStringLink(Object object) {
		return object instanceof OutputStringLink;
	}
	
	public Boolean isOutputFile(Object object) {
		return object instanceof OutputFile;
	}
	
	public Boolean isText(Object object) {
		return object instanceof Text;
	}
	
	public Boolean isFile(Object object) {
		return object instanceof File;
	}
	
	public Boolean isFileImage(Object object) {
		return object instanceof FileImage;
	}
	
	@Deprecated
	public Boolean isImage(Object object) {
		return object instanceof FileImage;
	}
	
	public Boolean isLink(Object object) {
		return object instanceof Link;
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
	
	public Boolean isMenuRenderTypeColumnContext(Object object) {
		return object instanceof MenuRenderTypeColumnContext;
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
	
	public String getGlobalMessagesTargetsIdentifiers() {
		if(globalMessagesTargetsIdentifiers == null) {
			globalMessagesTargetsIdentifiers = 
					":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetInlineComponentIdentifier()
					+","+":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetGrowlComponentIdentifier()
					+","+":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentIdentifier();
		}
		return globalMessagesTargetsIdentifiers;
	}
	
	/**/
	
	public static final String GLOBAL_FORM_IDENTIFIER = "form";
	
	public static final String GLOBAL_MESSAGES_OWNER_INLINE_IDENTIFIER = "globalMessagesOwnerInline";
	public static final String GLOBAL_MESSAGES_OWNER_GROWL_IDENTIFIER = "globalMessagesOwnerGrowl";
	public static final String GLOBAL_MESSAGES_OWNER_DIALOG_IDENTIFIER = "globalMessagesOwnerDialog";
	
	public static final String GLOBAL_MESSAGES_TARGET_INLINE_IDENTIFIER = "globalMessagesTargetInline";
	public static final String GLOBAL_MESSAGES_TARGET_GROWL_IDENTIFIER = "globalMessagesTargetGrowl";
	public static final String GLOBAL_MESSAGES_TARGET_DIALOG_IDENTIFIER = "globalMessagesTargetDialog";
	
	public static final String GLOBAL_MESSAGES_OWNER_INLINE_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+ConstantCharacter.COLON+GLOBAL_MESSAGES_OWNER_INLINE_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_OWNER_GROWL_CLIENT_IDENTIFIER = GLOBAL_MESSAGES_OWNER_GROWL_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_OWNER_DIALOG_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+ConstantCharacter.COLON+GLOBAL_MESSAGES_OWNER_DIALOG_IDENTIFIER;
	
	public static final String GLOBAL_MESSAGES_TARGET_INLINE_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+ConstantCharacter.COLON+GLOBAL_MESSAGES_TARGET_INLINE_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_TARGET_GROWL_CLIENT_IDENTIFIER = GLOBAL_MESSAGES_TARGET_GROWL_IDENTIFIER;
	public static final String GLOBAL_MESSAGES_TARGET_DIALOG_CLIENT_IDENTIFIER = GLOBAL_FORM_IDENTIFIER+ConstantCharacter.COLON+GLOBAL_MESSAGES_TARGET_DIALOG_IDENTIFIER;
	
}
