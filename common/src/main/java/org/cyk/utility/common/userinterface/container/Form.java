package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.output.OutputText;

public class Form extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Form> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Form> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Form> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Form> {

		public static class Adapter extends BuilderBase.Adapter.Default<Form> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Form.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	public static class Master extends Form implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/

		public static interface BuilderBase<OUTPUT extends Master> extends Form.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Master> extends Form.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Master> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Master> {

			public static class Adapter extends BuilderBase.Adapter.Default<Master> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Master.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
		}
	}
	
	public static class Detail extends Form implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/
		
		public void __add__(Input<?> input) {
			/*if(input.getLabel()!=null){
				layOut(input.getLabel());
			}*/
			layOut(input);
		}
		
		public Detail add(Collection<Input<?>> inputs) {
			if(CollectionHelper.getInstance().isNotEmpty(inputs))
				for(Input<?> input : inputs)
					__add__(input);
			return this;
		}
		
		public Detail add(Input<?>...inputs) {
			if(ArrayHelper.getInstance().isNotEmpty(inputs))
				add(Arrays.asList(inputs));
			return this;
		}
		
		public Detail addBreak(){
			layOutBreak();
			return this;
		}
		
		/**/

		@SuppressWarnings("unchecked")
		public static Object buildTarget(Detail detail){
			return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance()
					.getIfNotNullElseDefault(Builder.Target.Adapter.Default.DEFAULT_CLASS, Builder.Target.Adapter.Default.class)).setInput(detail).execute();
		}
		
		/**/
		
		public static interface BuilderBase<OUTPUT extends Detail> extends Form.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Detail> extends Form.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Detail> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Detail> {

			public static class Adapter extends BuilderBase.Adapter.Default<Detail> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Detail.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
			
			public static interface Target<OUTPUT,CONTROL,ROW,LABEL> extends org.cyk.utility.common.Builder<Detail, OUTPUT> {
				
				ROW createRow(OUTPUT model);
				String getType(Control control);
				CONTROL addControl(ROW row,Control control,String type);
				LABEL addLabel(ROW row,OutputText outputText);
				Target<OUTPUT,CONTROL,ROW,LABEL> link(CONTROL control,LABEL label);
				
				public static class Adapter<OUTPUT,ROW,CONTROL,LABEL> extends org.cyk.utility.common.Builder.Adapter.Default<Detail, OUTPUT> implements Target<OUTPUT,CONTROL,ROW,LABEL>,Serializable {
					private static final long serialVersionUID = 1L;
					
					public Adapter(Detail input, Class<OUTPUT> outputClass) {
						super(Detail.class, input, outputClass);
					}
					
					public static class Default<OUTPUT,CONTROL,ROW,LABEL> extends Target.Adapter<OUTPUT,ROW,CONTROL,LABEL> implements Serializable {
						private static final long serialVersionUID = 1L;
						
						@SuppressWarnings("unchecked")
						public static Class<? extends Target<?,?,?,?>> DEFAULT_CLASS = (Class<? extends Default<?,?,?,?>>) ClassHelper.getInstance().getByName(Default.class);
						
						public Default(Detail input, Class<OUTPUT> outputClass) {
							super(input, outputClass);
						}
						
						@SuppressWarnings("unchecked")
						public Default() {
							this(null, null);
							setOutputClass((Class<OUTPUT>) ClassHelper.getInstance().getParameterAt(getClass(), 0, Object.class));
						}
						
						@Override
						protected OUTPUT __execute__() {
							OUTPUT instance = ClassHelper.getInstance().instanciateOne(getOutputClass());
							for (Integer rowIndex = 0; rowIndex < getInput().getLayout().getArea().getWidth().getTo().intValue(); rowIndex++) {
								ROW row = createRow(instance);
								for (Component component : getInput().getLayout().getWhereAreaWidthFromEqual(rowIndex)) {
									LABEL label = null;
									if(component instanceof Input<?> && ((Input<?>)component).getLabel()!=null)
										label = addLabel(row, ((Input<?>)component).getLabel());
									CONTROL control = addControl(row, (Control) component, getType((Control) component));
									if(label!=null)
										link(control, label);
								}
							}
							return instance;
						}
						
					}
					
					@Override
					public ROW createRow(OUTPUT model) {
						return null;
					}
					
					@Override
					public CONTROL addControl(ROW row, Control control, String type) {
						return null;
					}
					
					@Override
					public LABEL addLabel(ROW row, OutputText outputText) {
						return null;
					}
					
					@Override
					public String getType(Control control) {
						return null;
					}
					
					@Override
					public Target<OUTPUT, CONTROL, ROW, LABEL> link(CONTROL control, LABEL label) {
						return null;
					}
				}
				
			}
		}
	
		/**/
		
	}

	/**/
	
	
}
