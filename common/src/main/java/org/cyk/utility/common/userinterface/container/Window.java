package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ListenerHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.DeviceType;
import org.cyk.utility.common.userinterface.Request;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;
import org.cyk.utility.common.userinterface.panel.NotificationDialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Window extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	protected DeviceType deviceType;
	protected ConfirmationDialog confirmationDialog = new ConfirmationDialog();
	protected Request requestComponent = new Request();
	protected NotificationDialog notificationDialog = new NotificationDialog();
	
	/**/
	
	@Override
	protected void initialisation() {
		notificationDialog.getPropertiesMap();
		super.initialisation();
		getPropertiesMap().setFullPage(Boolean.TRUE);
		getPropertiesMap().setLayout(createLayout());
		getPropertiesMap().setMainMenu(createMainMenu());
		getPropertiesMap().setContextMenu(createContextMenu());
	}
	
	public void listenBeforeRender(){}
	
	protected Object createLayout(){
		Object layout = ListenerHelper.getInstance().listenObject(Listener.MAP.get(getClass()), Listener.METHOD_NAME_CREATE_LAYOUT
				,MethodHelper.Method.Parameter.buildArray(Window.class,this));
		if(layout == null)
			layout = ListenerHelper.getInstance().listenObject(Listener.COLLECTION, Listener.METHOD_NAME_CREATE_LAYOUT
					,MethodHelper.Method.Parameter.buildArray(Window.class,this));
		return layout;
	}
	
	protected Menu createMainMenu(){
		return Menu.build(this, Menu.Type.MAIN,Menu.RenderType.BAR);
	}
	
	protected Menu createContextMenu(){
		return Menu.build(this, Menu.Type.CONTEXT,Menu.RenderType.PANEL);
	}
	
	public static interface BuilderBase<OUTPUT extends Window> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Window> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Window> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Window> {

		public static class Adapter extends BuilderBase.Adapter.Default<Window> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Window.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	public static interface Listener {
		
		Map<Class<? extends Window>,Collection<Listener>> MAP = new HashMap<Class<? extends Window>, Collection<Listener>>();
		Collection<Listener> COLLECTION = new ArrayList<Window.Listener>();
		
		String METHOD_NAME_CREATE_LAYOUT = "createLayout";
		Object createLayout(Window window);
		
		/**/
		
		public static class Adapter extends AbstractBean implements Listener , Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public Object createLayout(Window window) {
				return null;
			}
			
		}
	}
}
