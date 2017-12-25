package org.cyk.utility.common.userinterface.input.number;

import java.io.Serializable;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.input.Input;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputNumber<NUMBER extends Number> extends Input<NUMBER> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/

	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		if(isJavaServerFacesLibraryPrimefaces()){
			propertiesMap.setDecimalPlaces(0);
			//propertiesMap.setThousandSeparator(" ");
			propertiesMap.setDecimalSeparator(",");
		}
	}
	
	public static interface BuilderBase<OUTPUT extends InputNumber<?>> extends Input.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputNumber<?>> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputNumber<?>> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputNumber<?>> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputNumber<?>> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputNumber<?>>) ClassHelper.getInstance().getByName(InputNumber.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}