package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.LocaleHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputCalendar extends Input<Date> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/

	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		if(isJavaServerFacesLibraryPrimefaces()){
			propertiesMap.setPattern(Constant.Date.getPattern(LocaleHelper.getInstance().get(), Constant.Date.Part.DATE_ONLY, Constant.Date.Length.SHORT).getValue());
			propertiesMap.setTimeOnly(Boolean.FALSE);
		}
	}
	
	public static interface BuilderBase<OUTPUT extends InputCalendar> extends Input.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputCalendar> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputCalendar> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputCalendar> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputCalendar> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputCalendar>) ClassHelper.getInstance().getByName(InputCalendar.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}