package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.DeviceType;
import org.cyk.utility.common.userinterface.Request;
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
		super.initialisation();
		getPropertiesMap().setFullPage(Boolean.TRUE);
	}
	
	public void listenBeforeRender(){}
	
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
	
}
