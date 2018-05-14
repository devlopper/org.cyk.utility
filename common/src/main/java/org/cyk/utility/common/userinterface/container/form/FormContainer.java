package org.cyk.utility.common.userinterface.container.form;

import java.io.Serializable;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class FormContainer extends org.cyk.utility.common.userinterface.container.Container implements Serializable {
	private static final long serialVersionUID = 1L;
	
	static {
		ClassHelper.getInstance().map(Builder.Target.class, Builder.Target.Adapter.Default.class,Boolean.FALSE);
	}
	
	public FormContainer() {
		
	}
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		
	}
	
	@Override
	public Visible setLabel(OutputText label) {
		super.setLabel(label);
		getPropertiesMap().setHeader(getLabel());
		return this;
	}
	
	@Override
	protected OutputText instanciateLabel() {
		OutputText instance =  super.instanciateLabel();
		getPropertiesMap().setHeader(instance);
		return instance;
	}
	
	public FormContainer read(){
		return this;
	}
	
	public FormContainer write(){
		return this;
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends FormContainer> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends FormContainer> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends FormContainer> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<FormContainer> {

		public static class Adapter extends BuilderBase.Adapter.Default<FormContainer> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(FormContainer.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	
	

	/**/
	
	
}
