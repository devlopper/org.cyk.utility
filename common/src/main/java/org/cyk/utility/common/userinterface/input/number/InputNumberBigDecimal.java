package org.cyk.utility.common.userinterface.input.number;

import java.io.Serializable;
import java.math.BigDecimal;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.input.Input;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputNumberBigDecimal extends InputNumber<BigDecimal> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/

	public static interface BuilderBase<OUTPUT extends InputNumberBigDecimal> extends Input.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputNumberBigDecimal> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputNumberBigDecimal> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputNumberBigDecimal> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputNumberBigDecimal> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputNumberBigDecimal>) ClassHelper.getInstance().getByName(InputNumberBigDecimal.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}