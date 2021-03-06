package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.model.Area;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.form.Form;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.Watermark;
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Component extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static ContentType RENDER_AS_CONTENT_TYPE = ContentType.DEFAULT;
	public static JavaServerFacesHelper.Library JAVA_SERVER_FACES_LIBRARY = JavaServerFacesHelper.Library.DEFAULT;
	
	private static final String PROPERTY_STRING_VALUE_IDENTIFIER_LAYER  = "userinterface";
	
	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private static final Set<ComponentClass> CLASSES = new HashSet<>();
	
	//protected Constant.Action action;
	//protected Class<?> actionOnClass;
	protected Boolean onPrepareCallLoad;
	
	protected Object built;
	protected Boolean hasBeenBuilt;
	protected Component parent;
	protected CollectionHelper.Instance<Component> children;
	protected ContentType renderAsContentType = RENDER_AS_CONTENT_TYPE;
	
	/**/

	public Component() {
		//getPropertiesMap();//trigger settings
		if(Boolean.TRUE.equals(isJavaServerFacesLibraryPrimefaces()))
			constructorJavaServerFacesLibraryPrimefaces();
		
	}
	
	protected void constructorJavaServerFacesLibraryPrimefaces(){
		
	}
	
	@Override
	protected Properties instanciateProperties() {
		return super.instanciateProperties().setIdentifier(RandomHelper.getInstance().getAlphabetic(5)).setRendered(Boolean.TRUE);
	}
		
	public Component addOneChild(Component component){
		if(children == null)
			children = instanciateChildrenCollection();
		children.addOne(component);
		component.setParent(this);
		if(Boolean.TRUE.equals(hasBeenBuilt))
			component.build();
		return this;
	}
	
	public Component addManyChild(Collection<Component> components){
		if(CollectionHelper.getInstance().isNotEmpty(components))
			for(Component component : components)
				addOneChild(component);
		return this;
	}
	
	public Component addManyChild(Component...components){
		if(ArrayHelper.getInstance().isNotEmpty(components))
			addManyChild(Arrays.asList(components));
		return this;
	}
	
	/**
	 * prepare structure of the component
	 * @return
	 */
	public Component prepare(){
		return this;
	}
	
	/**
	 * load data in the structure
	 * @return
	 */
	public Component load(){
		return this;
	}
	
	protected CollectionHelper.Instance<Component> instanciateChildrenCollection(){
		CollectionHelper.Instance<Component> collection = new CollectionHelper.Instance<Component>();
		collection.setElementClass(Component.class);
		return collection;
	}
	
	/**
	 * build the target and set the built property
	 * @return
	 */
	public Component build(){
		if(children!=null)
			new CollectionHelper.Iterator.Adapter.Default<Component>(children.getElements()){
				private static final long serialVersionUID = 1L;
				@Override
				protected void __executeForEach__(Component component) {
					component.build();
				}
			}.execute();
		//built = ListenerHelper.getInstance()
		//		.listenObject(Listener.COLLECTION, Listener.METHOD_NAME_BUILD, MethodHelper.Method.Parameter.buildArray(Component.class,this));
		built = ClassHelper.getInstance().instanciateOne(Listener.class).build(this);
		hasBeenBuilt = Boolean.TRUE;
		return this;
	}
	
	public Component _setPropertyUrl(UniformResourceLocatorHelper.Stringifier stringifier){
		getPropertiesMap().setUrl(stringifier.execute());
		return this;
	}
	
	public Component _setPropertyUrl(String pathIdentifier,Object...queryKeyValue){
		getPropertiesMap().setUrl(UniformResourceLocatorHelper.getInstance().stringify(pathIdentifier,queryKeyValue));
		return this;
	}
	
	public Component _setPropertyUrl(Constant.Action action,Object object,Object...queryKeyValue){
		getPropertiesMap().setUrl(UniformResourceLocatorHelper.getInstance().stringify(action, object, queryKeyValue));
		return this;
	}
	
	public Component _setPropertyUrlRead(String pathIdentifier,Object...queryKeyValue){
		getPropertiesMap().setUrlRead(UniformResourceLocatorHelper.getInstance().stringify(pathIdentifier,queryKeyValue));
		return this;
	}
	
	public Component _setPropertyUrlRead(Constant.Action action,Object object,Object...queryKeyValue){
		getPropertiesMap().setUrlRead(UniformResourceLocatorHelper.getInstance().stringify(action, object, queryKeyValue));
		return this;
	}
	
	public Component _setPropertyIcon(Object icon){
		getPropertiesMap().setIcon(icon);
		return this;
	}
	
	public Component _setPropertyAction(Object action){
		getPropertiesMap().setAction(action);
		return this;
	}
	
	public Object _getPropertyAction(){
		return getPropertiesMap().getAction();
	}
	
	public Object getPropertyAction(){
		return getPropertiesMap().getAction();
	}
	
	public Component _setPropertyUsingOutputText(String name,Object value){
		OutputText outputText = null;
		if(getPropertiesMap().get(name) == null){
			getPropertiesMap().set(name,outputText = new OutputText());
		}else{
			if(getPropertiesMap().get(name) instanceof OutputText)
				outputText = (OutputText) getPropertiesMap().get(name);
		}
		
		if(outputText == null){
			getPropertiesMap().set(name,value);	
		}else{
			outputText.getPropertiesMap().setValue(value);
		}
		return this;
	}
	
	public Component _setPropertyTitle(Object title){
		_setPropertyUsingOutputText(Properties.TITLE, title);
		return this;
	}
	
	public Component _setPropertyTitleFromStringIdentifier(String identifier){
		_setPropertyTitle(StringHelper.getInstance().get(identifier, new Object[]{}));
		return this;
	}
	
	public Component _setPropertyContentTitle(Object contentTitle){
		OutputText outputText = (OutputText) getPropertiesMap().getContentTitle();
		if(getPropertiesMap().getContentTitle() == null){
			outputText = new OutputText();
		}else{
			if(getPropertiesMap().getContentTitle() instanceof OutputText)
				outputText = (OutputText) getPropertiesMap().getContentTitle();
		}
		
		if(outputText == null){
			getPropertiesMap().setContentTitle(contentTitle);	
		}else{
			outputText.getPropertiesMap().setValue(contentTitle);
		}
		return this;
	}
	
	public Component setPropertyValue(Object value){
		getPropertiesMap().setValue(value);
		return this;
	}
	
	public Component addPropertyStyleClass(String...classes){
		Object separator = isJavaServerFacesLibraryPrimefaces() ? Constant.CHARACTER_SPACE.toString() : Constant.CHARACTER_SPACE.toString();
		getPropertiesMap().addString(Properties.STYLE_CLASS, separator, classes);
		return this;
	}
	
	public Object getPropertyHeaderPropertyValue(){
		OutputText outputText = null;
		if(getPropertiesMap().getHeader() instanceof OutputText)
			outputText = (OutputText) getPropertiesMap().getHeader();
		if(outputText == null)
			return null;
		return outputText.getPropertiesMap().getValue();
	}
	
	/**/
	
	@Override
	public String toString() {
		return super.toString() + (propertiesMap == null ? Constant.EMPTY_STRING : propertiesMap);
	}
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Component> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {

		BuilderBase<OUTPUT> setComponentParent(Component componentParent);
		
		public static class Adapter<OUTPUT extends Component> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			protected Component componentParent;
			
			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}
			
			@Override
			public BuilderBase<OUTPUT> setComponentParent(Component componentParent) {
				return null;
			}

			/**/

			public static class Default<OUTPUT extends Component> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
				@Override
				public BuilderBase<OUTPUT> setComponentParent(Component componentParent) {
					this.componentParent = componentParent;
					return this;
				}
			}
		}
		
		/**/
		
		public static interface Target<COMPONENT,OUTPUT> extends org.cyk.utility.common.Builder<COMPONENT, OUTPUT> {
			
			public static class Adapter<COMPONENT,OUTPUT> extends org.cyk.utility.common.Builder.Adapter.Default<COMPONENT, OUTPUT> implements Target<COMPONENT,OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;
				
				public Adapter(Class<COMPONENT> componentClass,COMPONENT input, Class<OUTPUT> outputClass) {
					super(componentClass, input, outputClass);
				}
				
				public static class Default<COMPONENT,OUTPUT> extends Target.Adapter<COMPONENT,OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<COMPONENT> componentClass,COMPONENT input, Class<OUTPUT> outputClass) {
						super(componentClass,input, outputClass);
					}
					
					@SuppressWarnings("unchecked")
					public Default() {
						this(null,null, null);
						setOutputClass((Class<OUTPUT>) ClassHelper.getInstance().getParameterAt(getClass(), 0, Object.class));
					}
					
					@Override
					protected OUTPUT __execute__() {
						OUTPUT instance = ClassHelper.getInstance().instanciateOne(getOutputClass());
						
						return instance;
					}		
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Component> {

		public static class Adapter extends BuilderBase.Adapter.Default<Component> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Component.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Visible extends Component implements Serializable {
		private static final long serialVersionUID = 1L;

		protected Area area = new Area();
		protected OutputText label;
		
		/**/
		
		@Override
		protected void listenPropertiesInstanciated(Properties propertiesMap) {
			super.listenPropertiesInstanciated(propertiesMap);
			__addPropertyStyleClass__(propertiesMap);
		}
		
		protected void __addPropertyStyleClass__(Properties propertiesMap){
			if(isJavaServerFacesLibraryPrimefaces())
				propertiesMap.addString(Properties.STYLE_CLASS, Constant.CHARACTER_SPACE.toString(), generatePropertyStyleClass());
		}
		
		protected String generatePropertyStyleClass(){
			return CascadeStyleSheetHelper.getInstance().getClass(getPropertyStyleClassClasses());
		}
		
		protected Class<?>[] getPropertyStyleClassClasses(){
			return new Class<?>[]{getClass()};
		}
		
		public Visible _setLabelPropertyValue(String value){
			getLabel().getPropertiesMap().setValue(value);
			return this;
		}
		
		public OutputText getLabel(){
			if(label == null)
				label = instanciateLabel();
			return label;
		}
		
		public Visible setLabel(OutputText label){
			this.label = label;
			if(label!=null){
				if(getPropertiesMap().getTitle()==null)
					getPropertiesMap().setTitle(label.getPropertiesMap().getValue());
			}
			__setWatermarkFromLabel__();
			return this;
		}
		
		protected OutputText instanciateLabel(){
			return	new OutputText();
		}
		
		public Visible __setWatermarkFromLabel__(){
			Watermark watermark = (Watermark)getPropertiesMap().getWatermark();
			if(watermark==null){
				getPropertiesMap().setWatermark(watermark = new Watermark());
			}
			watermark.getPropertiesMap().setValue(label.getPropertiesMap().getValue());
			return this;
		}

		public Visible setLabelFromIdentifier(String identifier){
			if(StringHelper.getInstance().isBlank(identifier))
				getLabel().getPropertiesMap().setValue(null);
			else
				getLabel().getPropertiesMap().setValue(StringHelper.getInstance().get(identifier, new Object[]{}));
			__setWatermarkFromLabel__();
			if(getPropertiesMap().getTitle()==null)
				getPropertiesMap().setTitle(getLabel().getPropertiesMap().getValue());
			return this;
		}
		
		public Visible __setPropertyLabelFromLabelValue__(){
			getPropertiesMap().setTitle(getLabel().getPropertiesMap().getValue());
			return this;
		}
		
		public Visible __setPropertyTitleFromLabelValue__(){
			getPropertiesMap().setTitle(getLabel().getPropertiesMap().getValue());
			return this;
		}
		
		public Visible setPropertyLabel(Object object){
			getPropertiesMap().setLabel(object);
			return this;
		}
		
		public Visible setLabelPropertyValue(Object object){
			getLabel().getPropertiesMap().setValue(object);
			return this;
		}
		
		public Visible __setLabelValueBasedOnActionProperty__(){
			if(getPropertiesMap().getAction() instanceof Constant.Action){
				_setLabelPropertyValue(StringHelper.getInstance().getVerb((Constant.Action)getPropertiesMap().getAction()));
				//_setLabelPropertyValue(StringHelper.getInstance().getPhrase((Constant.Action)getPropertiesMap().getAction()
				//		, (Class<?>)getPropertiesMap().getActionOnClass()));
			}
			return this;
		}
		
		public Visible __setPropertyIconBasedOnPropertyAction__(){
			if(getPropertiesMap().getAction() instanceof Constant.Action){
				switch( (Constant.Action)getPropertiesMap().getAction() ){
				case CONSULT:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.EYE);break;
				case DELETE:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.TRASH);break;
				case CREATE:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.PLUS);break;
				case LIST:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.LIST);break;
				case LOGIN:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.SIGNIN);break;
				case LOGOUT:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.SIGNOUT);break;
				case PRINT:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.PRINT);break;
				case READ:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.EYE);break;
				case SEARCH:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.SEARCH);break;
				case SELECT:break;
				case UPDATE:getPropertiesMap().setIcon(IconHelper.Icon.FontAwesome.EDIT);break;
				}
			}
			
			return this;
		}
				
		public Visible setLength(Number length){
			getArea().getLength().setDistance(length);
			return this;
		}
		
		public Visible setWidth(Number width){
			getArea().getWidth().setDistance(width);
			return this;
		}
		
		public Visible _setLabelPropertyRendered(Object rendered){
			getLabel().getPropertiesMap().setRendered(rendered);
			return this;
		}
		
		public Visible _setPropertyTitleFromLabel(){
			getPropertiesMap().setTitle(getLabel().getPropertiesMap().getValue());
			return this;
		}
		
		public Visible _setPropertyEvent(String name,String process,String update,CommandHelper.Command listener){
			Event event = new Event();
			event.getPropertiesMap().setEvent(name);
			event.getPropertiesMap().setProcess(process);
			event.getPropertiesMap().setUpdate(update);
			event.getPropertiesMap().setDisabled(Boolean.FALSE);
			if(listener!=null)
				event.setListener(listener);
			getPropertiesMap().setEvent(event);
			return this;
		}
		
		public Visible _setPropertyOnClick(String onClick,Boolean actionable){
			getPropertiesMap().setOnClick(onClick);
			return this;
		}
		
		public Visible setPropertyHeaderPropertyValue(Object value) {
			if(getPropertiesMap().getHeader() == null)
				getPropertiesMap().setHeader(new OutputText());
			((OutputText)getPropertiesMap().getHeader()).setPropertyValue(value);
			return this;
		}
		
		/**/
		
		public static interface BuilderBase<OUTPUT extends Visible> extends Component.BuilderBase<OUTPUT> {

			BuilderBase<OUTPUT> setPropertyHeaderPropertyValue(Object value);
			BuilderBase<OUTPUT> setPropertyField(Object value);
			BuilderBase<OUTPUT> setPropertyFieldName(Object value);
			
			public static class Adapter<OUTPUT extends Visible> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				/**/

				public static class Default<OUTPUT extends Visible> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
					
					@Override
					public BuilderBase<OUTPUT> setPropertyHeaderPropertyValue(Object value) {
						if(getPropertiesMap().getHeader() == null)
							getPropertiesMap().setHeader(new OutputText());
						((OutputText)getPropertiesMap().getHeader()).setPropertyValue(value);
						return this;
					}
					
					@Override
					public BuilderBase<OUTPUT> setPropertyField(Object value) {
						getPropertiesMap().setField(value);
						return this;
					}
					
					@Override
					public BuilderBase<OUTPUT> setPropertyFieldName(Object value) {
						getPropertiesMap().setFieldName(value);
						return this;
					}
				}
				
				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
				@Override
				public BuilderBase<OUTPUT> setPropertyHeaderPropertyValue(Object value) {
					return null;
				}
				
				@Override
				public BuilderBase<OUTPUT> setPropertyField(Object value) {
					return null;
				}
				
				@Override
				public BuilderBase<OUTPUT> setPropertyFieldName(Object value) {
					return null;
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Visible> {

			public static class Adapter extends BuilderBase.Adapter.Default<Visible> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Visible.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
		}
	
		/**/

	}
	
	public static class Invisible extends Component implements Serializable {
		private static final long serialVersionUID = 1L;

		/**/

		public static interface BuilderBase<OUTPUT extends Invisible> extends Component.BuilderBase<OUTPUT> {

			public static class Adapter<OUTPUT extends Invisible> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}

				/**/

				public static class Default<OUTPUT extends Invisible> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
		}
		
		public static interface Builder extends BuilderBase<Invisible> {

			public static class Adapter extends BuilderBase.Adapter.Default<Invisible> implements Builder, Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Invisible.class);
				}

				/**/

				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

				}
			}
		}
	}

	/**/
	
	public static interface Listener {
		
		//@Deprecated Collection<Listener> COLLECTION = new ArrayList<Listener>();
		
		String getTemplate(Component component);
		
		String METHOD_NAME_BUILD = "build";
		Object build(Component component);
		
		String METHOD_NAME_LISTEN_INSTANCIATE_ONE = "listenInstanciateOne";
		void listenInstanciateOne(Component component);
		
		String METHOD_NAME_LISTEN_INSTANCIATE_ONE_NULL_SPECIFIC_CLASS = "listenInstanciateOneNullSpecificClass";
		void listenInstanciateOneNullSpecificClass(Component component);
		
		String METHOD_NAME_LISTEN_INSTANCIATE_ONE_NON_NULL_SPECIFIC_CLASS = "listenInstanciateOneNonNullSpecificClass";
		void listenInstanciateOneNonNullSpecificClass(Component component);

		public static class Adapter extends AbstractBean implements Listener , Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			public String getTemplate(Component component) {
				return null;
			}
			
			@Override
			public Object build(Component component) {
				return null;
			}

			@Override
			public void listenInstanciateOne(Component component) {}

			@Override
			public void listenInstanciateOneNullSpecificClass(Component component) {}

			@Override
			public void listenInstanciateOneNonNullSpecificClass(Component component) {}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void listenInstanciateOneNonNullSpecificClass(Component component) {
					component.prepare();
				}
			}
		}
	}

	/**/
	
	public static String getPropertyStringValueIdentifier(String classGroup,String className,String name){
		return Properties.getStringValueIdentifier(PROPERTY_STRING_VALUE_IDENTIFIER_LAYER, classGroup, className, name);
	}
	
	public static String getPropertyStringValueFromIdentifier(String classGroup,String className,String name){
		return Properties.getStringValueFromIdentifier(PROPERTY_STRING_VALUE_IDENTIFIER_LAYER, classGroup, className, name);
	}
	
	public static Constant.Action[] getActionsSharingClass(Component parent,Constant.Action[] actions,Class<?> actionOnClass,Object key,Class<?> nullValue,Boolean excludeActions){
		Set<Constant.Action> set = new LinkedHashSet<>();
		for(Constant.Action action : actions){
			if(!Boolean.TRUE.equals(excludeActions))
				set.add(action);
			if(Constant.Action.CREATE.equals(action)){
				
			}else if(Constant.Action.READ.equals(action)){
				set.addAll(Arrays.asList(Constant.Action.CONSULT,Constant.Action.CREATE,Constant.Action.UPDATE,Constant.Action.DELETE));
			}else if(Constant.Action.UPDATE.equals(action)){
				set.addAll(Arrays.asList(Constant.Action.CREATE));
			}else if(Constant.Action.DELETE.equals(action)){
				set.addAll(Arrays.asList(Constant.Action.READ,Constant.Action.CREATE,Constant.Action.UPDATE));
			}else if(Constant.Action.CONSULT.equals(action)){
				set.addAll(Arrays.asList(Constant.Action.READ,Constant.Action.CREATE,Constant.Action.UPDATE,Constant.Action.DELETE));
			}

		}
			
		return set.toArray(new Constant.Action[]{});
	}
	
	public static Constant.Action[] getActionsSharingClass(Component parent,Constant.Action[] actions,Class<?> actionOnClass,Object key,Class<?> nullValue){
		return getActionsSharingClass(parent, actions, actionOnClass, key, nullValue, Boolean.FALSE);
	}
	
	@SuppressWarnings("unchecked")
	public static void __setClass__(Class<? extends Component> parentClass,Constant.Action[] actions,Class<?> actionOnClass,Object key,Class<?> clazz){
		if(ArrayHelper.getInstance().isNotEmpty(actions))
			for(Constant.Action action : actions)
				CLASSES.add(new ComponentClass(parentClass,action, actionOnClass, key, (Class<? extends Component>) clazz));
	}
	
	public static void __setClass__(Class<? extends Component> parentClass,Constant.Action action,Class<?> actionOnClass,Object key,Class<?> clazz){
		__setClass__(parentClass, new Constant.Action[]{action}, actionOnClass, key, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends Component> getClass(Component parent,Constant.Action[] actions,Class<?> actionOnClass,Object key,Class<?> nullValue){
		actions = getActionsSharingClass(parent, actions, actionOnClass, key, nullValue);
		for(Constant.Action action : actions)
			for(ComponentClass componentClass : CLASSES)
				if(
						( componentClass.getParentClass()==null || parent==null || componentClass.getParentClass().equals(parent.getClass()) )
						&& ( componentClass.getAction().equals(action) )
						&& ( componentClass.getActionOnClass().equals(actionOnClass) )
						&& ( componentClass.getKey()==null || componentClass.getKey().equals(key) ) 
					)
					return componentClass.getClazz();
		return (Class<? extends Component>) nullValue;
	}
	
	public static Class<? extends Component> getClass(Component parent,Constant.Action action,Class<?> actionOnClass,Object key,Class<?> nullValue){
		return getClass(parent, new Constant.Action[]{ action }, actionOnClass, key, nullValue);
	}
	
	public static Class<?> getClass(Component parent,Constant.Action action,Class<?> actionOnClass,Class<?> nullValue){
		return getClass(parent,action, actionOnClass, null, nullValue);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Component> T get(Component parent,Class<T> componentClass,Constant.Action action,Class<?> actionOnClass,Object key
			,Collection<Object> actionOnClassInstances,Class<? extends Component> nullClassValue){
		//System.out.println("Component.get() C : "+componentClass+" N : "+nullClassValue);
		Class<T> aClass = componentClass.equals(nullClassValue) ? (Class<T>) getClass(parent,action, actionOnClass, key, nullClassValue) : componentClass;
		//System.out.println("Component.get() A : "+aClass);
		T component = (T) ClassHelper.getInstance().instanciateOne(aClass);
		component.setParent(parent);
		component.getPropertiesMap().setActionOnClass(actionOnClass);
		component.getPropertiesMap().setAction(action);
		if(parent!=null)
			component.getPropertiesMap().addString(Properties.STYLE_CLASS,Constant.CHARACTER_SPACE, CascadeStyleSheetHelper.getInstance().getClass(parent.getClass()));
		
		if(component instanceof Form){
			//if(parent instanceof Window)
			//	component.getPropertiesMap().add(Properties.STYLE_CLASS, CascadeStyleSheetHelper.getInstance().getClass(parent.getClass()));
			Object object = parent instanceof Window ? ((Window)parent).getActionOnClassInstances().iterator().next() : null;//TODO how to handle many ? use key to point to the adequate form
			Form master = (Form) component;
			master.setObject(object);
			master._setPropertyAction(action);
			FormDetail detail = master.getDetail();
			if(detail == null)
				detail = master.instanciateDetail();
		}else if(component instanceof DataTable){
			//DataTable dataTable = (DataTable) component;
			//dataTable.getPropertiesMap().setActionOnClass(actionOnClass);
			//dataTable.getPropertiesMap().setAction(action);
			
		}else if(component instanceof Hierarchy){
			//Hierarchy hierarchy = (Hierarchy) component;
			
		}
		/*ListenerHelper.getInstance().listen(Listener.COLLECTION, Listener.METHOD_NAME_LISTEN_INSTANCIATE_ONE, MethodHelper.Method.Parameter
				.buildArray(Component.class,component));
		*/
		if(aClass.equals(nullClassValue)){
			/*ListenerHelper.getInstance().listen(Listener.COLLECTION, Listener.METHOD_NAME_LISTEN_INSTANCIATE_ONE_NULL_SPECIFIC_CLASS, MethodHelper.Method.Parameter
					.buildArray(Component.class,component));
			*/
			if(component instanceof Form){
				Form master = (Form) component;
				FormDetail detail = master.getDetail();
				if(Boolean.TRUE.equals(master.getEditable())){
					for(Input<?> input : Input.get(detail, master.getObject()))
						detail.add(input).addBreak();
				}else {
					for(Output output : Output.get(detail, master.getObject()))
						detail.add(output).addBreak();	
				}
			}
		}else{
			/*ListenerHelper.getInstance().listen(Listener.COLLECTION, Listener.METHOD_NAME_LISTEN_INSTANCIATE_ONE_NON_NULL_SPECIFIC_CLASS, MethodHelper.Method.Parameter
					.buildArray(Component.class,component));
			*/
			if(component instanceof Form){
				//Form.Master master = (Master) component;
			}else if(component instanceof DataTable){
				DataTable dataTable = (DataTable) component;
				dataTable.setOnPrepareCallLoad(!Boolean.TRUE.equals(ClassHelper.getInstance().isLazy(actionOnClass)));
				dataTable.getPropertiesMap().setIfNull(Properties.ON_PREPARE_ADD_MENU, ClassHelper.getInstance().isIdentified(actionOnClass));
				dataTable.getPropertiesMap().setIfNull(Properties.ON_PREPARE_ADD_COLUMN_ACTION, ClassHelper.getInstance().isIdentified(actionOnClass));
				if(dataTable.getOnPrepareCallLoad() == null)
					dataTable.setOnPrepareCallLoad(ClassHelper.getInstance().isIdentified(actionOnClass));
				
			}
			component.prepare();
			if(component instanceof Form){
				//Form.Master master = (Master) component;
			}
		}
		return component;
	}
	
	public static <T extends Component> T get(Component parent,Class<T> componentClass,Constant.Action action,Class<?> actionOnClass,Object key
			,Collection<Object> actionOnClassInstances){
		return get(parent, componentClass, action, actionOnClass, key,actionOnClassInstances, componentClass);
	}
	
	public static <T extends Component> T get(Window window,Class<T> componentClass,Class<? extends Component> nullClassValue){
		return get(window,componentClass, (Constant.Action)window.getPropertiesMap().getAction(), (Class<?>)window.getPropertiesMap().getActionOnClass(), window.getActionKey(),window.getActionOnClassInstances(),nullClassValue);
	}
	
	public static <T extends Component> T get(Window window,Class<T> componentClass){
		return get(window, componentClass, componentClass);
	}
	
	public static void clearClasses(){
		CLASSES.clear();
	}
	
	public static Boolean isJavaServerFacesLibraryPrimefaces(){
		return JavaServerFacesHelper.Library.PRIMEFACES.equals(JAVA_SERVER_FACES_LIBRARY);
	}
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor @EqualsAndHashCode(of={"parentClass","action","actionOnClass","key"})
	public static class ComponentClass implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Class<? extends Component> parentClass;
		private Constant.Action action;
		private Class<?> actionOnClass;
		private Object key;
		
		private Class<? extends Component> clazz;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
	
	/**/
	
	public static class ClassLocator extends org.cyk.utility.common.ClassLocator implements Serializable {

		private static final long serialVersionUID = -3187769614985951029L;

		private String action;
		private Class<?> componentClass;
		private String className;
		
		public ClassLocator(Class<?> componentClass,String action,String className) {
			this.componentClass = componentClass;
			this.action = action;
			this.className = className;
			setClassType(this.action+this.componentClass.getSimpleName());
			Listener listener = new Listener.Adapter(){
				private static final long serialVersionUID = -979036256355287919L;

				@Override
				public Boolean isLocatable(Class<?> basedClass) {
					return Boolean.TRUE;
				}
			};
			
			listener.setGetNameMethod(new GetOrgCykSystem(this));
			getClassLocatorListeners().add(listener);
		}
		
		public ClassLocator(Class<?> componentClass,String action) {
			this(componentClass,action,componentClass.getSimpleName());
		}
		
		public ClassLocator(Class<?> componentClass,Constant.Action action,String className) {
			this(componentClass,StringHelper.getInstance().applyCaseType(action.name(), StringHelper.CaseType.FURL),className);
		}
		
		public ClassLocator(Class<?> componentClass,Constant.Action action) {
			this(componentClass,action,componentClass.getSimpleName());
		}
		
		@Override
		protected Class<?> getDefault(Class<?> aClass) {
			return componentClass;
		}
		
		/**/
		
		public static class GetOrgCykSystem extends Listener.AbstractGetOrgCykSystem {
			private static final long serialVersionUID = 1L;

			public static String WINDOW = "Window";
			public static String[] MODULE_PREFIXES = {"ui.web.primefaces.page"};
			public static String NAME_TOKEN_TO_REPLACE = "org.cyk.system.ui.";
			public static String NAME_TOKEN_REPLACEMENT = "org.cyk.ui.";
			
			private ClassLocator classLocator;
			
			public GetOrgCykSystem(ClassLocator classLocator) {
				this.classLocator = classLocator;
			}
			
			@Override
			protected String getBaseClassPackageName() {
				return "model";
			}
			
			@Override
			protected String[] getSystemIdentifiers(Class<?> aClass) {
				return ArrayUtils.addAll(super.getSystemIdentifiers(aClass), "") ;
			}
			
			@Override
			protected String[] getModulePrefixes() {
				return MODULE_PREFIXES;
			}
			
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{classLocator.className,classLocator.action+WINDOW+"$"+classLocator.className};
			}
			
			@Override
			protected String[] __execute__(Class<?> aClass) {
				String[] names =  super.__execute__(aClass);
				for(int index = 0 ; index < names.length ; index++)
					if(StringUtils.contains(names[index], NAME_TOKEN_TO_REPLACE))
						names[index] = StringUtils.replace(names[index],NAME_TOKEN_TO_REPLACE, NAME_TOKEN_REPLACEMENT);
				return names;
			}
		}
		
	}
	
	/**/
	
	public static class LabelPropertyValueComparator implements java.util.Comparator<Component> {

		@Override
		public int compare(Component o1, Component o2) {
			if(o1 instanceof Visible && o2 instanceof Visible && ((Visible)o1).getLabel()!=null && ((Visible)o1).getLabel().getPropertiesMap().getValue()!=null
					&& ((Visible)o2).getLabel()!=null && ((Visible)o2).getLabel().getPropertiesMap().getValue()!=null){
				return ((String)((Visible)o1).getLabel().getPropertiesMap().getValue()).compareTo( ((String)((Visible)o2).getLabel().getPropertiesMap().getValue()) );
			}
			return 0;
		}
		
	}
}