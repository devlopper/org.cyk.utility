package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class IconHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static IconHelper INSTANCE;
	
	public static IconHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new IconHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Icon.Mapping getMapping(){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance().getIfNotNullElseDefault(Icon.Mapping.Adapter.Default.DEFAULT_CLASS, Icon.Mapping.Adapter.Default.class));
	}
	
	@SuppressWarnings("unchecked")
	public <RESULT> RESULT map(Class<RESULT> aClass,Icon icon){
		return (RESULT) getMapping().setInput(icon).execute();
	}
	
	public String map(Icon icon){
		return map(String.class,icon);
	}
	
	public static enum Icon {
		FILTER,TRASH,PLUS,PLUS_CIRCLE,PLUS_SQUARE,MINUS,MINUS_CIRCLE,MINUS_SQUARE,
	
		ACTION_GO_BACK,ACTION_OK,ACTION_APPLY,ACTION_SAVE,ACTION_ADD,ACTION_UPDATE,ACTION_DELETE,ACTION_DUPLICATE,ACTION_CLEAR,ACTION_EDIT,ACTION_EDIT_MANY,ACTION_CANCEL,ACTION_REMOVE,ACTION_OPEN
		,ACTION_ADMINISTRATE,ACTION_HELP,ACTION_PREVIEW,ACTION_SEARCH,ACTION_EXPORT,ACTION_EXPORT_PDF,ACTION_EXPORT_EXCEL,ACTION_LOGIN,ACTION_LOGOUT,ACTION_PRINT,ACTION_DOWNLOAD
		
		,THING_APPLICATION,THING_LICENCE,THING_TOOLS,THING_CALENDAR,THING_AGENDA,THING_USERACCOUNT,THING_CONTROLPANEL,THING_LIST,THING_FOLDER_COLLAPSED,THING_FOLDER_EXPANDED
		,THING_TABLE, THING_SECURITY, THING_NOTIFICATIONS, THING_HELP, THING_REPORT, THING_HOME, THING_CONNECTED,THING_URL,THING_ROLE,THING_MONEY,THING_FILE,THING_LOCATION_ARROW
		
		,PERSON, ACTION_SET
		
		;
		
		public static interface Mapping extends org.cyk.utility.common.Action<Icon,Object> {
			
			public static class Adapter extends org.cyk.utility.common.Action.Adapter.Default<Icon,Object> implements Mapping,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Icon icon) {
					super("map icon", Icon.class, icon, Object.class);
				}
				
				/**/
				
				public static class Default extends Mapping.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					public static Class<Mapping> DEFAULT_CLASS = (Class<Mapping>) ClassHelper.getInstance().getByName(Default.FontAwesome.class);
					
					public Default(Icon icon) {
						super(icon);
					}	
					
					public Default() {
						this(null);
					}	
					
					@Override
					protected Object __execute__() {
						return getInput();
					}
				
					/**/
					
					public static class FontAwesome extends Default implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@Override
						protected Object __execute__() {
							switch(getInput()){
							case FILTER : return "fa fa-filter";
							case TRASH : return "fa fa-filter";
							case PLUS : return "fa fa-plus";
							case PLUS_CIRCLE : return "fa fa-plus-circle";
							case PLUS_SQUARE : return "fa fa-plus-square";
							case MINUS : return "fa fa-minus";
							case MINUS_CIRCLE : return "fa fa-minus-circle";
							case MINUS_SQUARE : return "fa fa-minus-square";
							
							case ACTION_ADD:return "fa fa-plus-circle";
							case ACTION_CANCEL:return "ui-icon-close";
							case ACTION_OPEN:return "fa fa-folder-open";
							case ACTION_ADMINISTRATE:return "ui-icon-gear";
							case ACTION_HELP:return "ui-icon-help";
							case ACTION_APPLY:return  "fa fa-save" /*"fa fa-check" /*"ui-icon-check"*/;
							case ACTION_EDIT:return "fa fa-pencil";
							case ACTION_GO_BACK:return "ui-icon-arrow-e";
							case ACTION_OK:return "fa fa-check";
							case ACTION_SAVE:return "fa fa-save";
							case ACTION_SEARCH:return "ui-icon-search";
							case ACTION_PREVIEW:return "ui-icon-image";
							case ACTION_LOGOUT:return "ui-icon-extlink";
							case ACTION_EXPORT:return "ui-icon-document";
							case ACTION_PRINT:return "ui-icon-print";
							case ACTION_CLEAR: return "ui-icon-trash";
							case ACTION_EXPORT_EXCEL: return "ui-icon-";
							case ACTION_EXPORT_PDF: return "ui-icon-";
							case ACTION_SET: return "ui-icon-wrench";
							case ACTION_DOWNLOAD: return "fa fa-download";
							case ACTION_UPDATE: return "fa fa-edit";
							case ACTION_REMOVE:return "fa fa-remove";
							case ACTION_DELETE: return "fa fa-trash";
							
							case THING_APPLICATION: return "ui-icon-";
							case THING_CALENDAR: return "ui-icon-calendar";
							case THING_CONTROLPANEL: return "ui-icon-gear";
							case THING_HELP: return "ui-icon-help";
							case THING_LICENCE: return "ui-icon-document";
							case THING_LIST: return "ui-icon-document";
							case THING_NOTIFICATIONS: return "ui-icon-flag";
							case THING_REPORT: return "ui-icon-document";
							case THING_SECURITY: return "ui-icon-key";
							case THING_TOOLS: return "ui-icon-wrench";
							case THING_URL: return "fa fa-link";
							case THING_ROLE: return "fa fa-lock";
							case THING_USERACCOUNT: return "fa fa-key";
							case THING_HOME: return "ui-icon-home";
							case THING_CONNECTED: return "ui-icon-newin";
							case THING_FOLDER_COLLAPSED: return "fa fa-folder";
							case THING_FOLDER_EXPANDED: return "fa fa-folder-open";
							case THING_TABLE: return "fa fa-table";
							case THING_MONEY: return "fa fa-money";
							case THING_AGENDA:return "fa fa-calendar-o";
							
							case PERSON:return "fa fa-user";
							default:return null;
							}
						}
					}
				}	
			}
		}
	}
	
	
	
	/**/
	
	public static interface Listener {
		
		
		
	}
	
	/**/
	
	
}
