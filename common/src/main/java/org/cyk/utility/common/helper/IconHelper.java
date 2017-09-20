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
					public static Class<Mapping> DEFAULT_CLASS = (Class<Mapping>) ClassHelper.getInstance().getByName(Default.class);
					
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
				}	
			}
		}
	}
	
	
	
	/**/
	
	public static interface Listener {
		
		
		
	}
	
	/**/
	
	
}
