package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.helper.ClassHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractBuilder<OBJECT> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 2176086848500759488L;

	protected Class<OBJECT> aClass;
	protected OBJECT instance;
	
	public AbstractBuilder(Class<OBJECT> aClass) {
		super();
		this.aClass = aClass;
	}
	
	@SuppressWarnings("unchecked")
	public Class<OBJECT> getAClass(){
		if(aClass==null)
			aClass = (Class<OBJECT>) new ClassHelper().getParameterAt(getClass(), 0,Object.class);
		return aClass;
	}

	public AbstractBuilder<OBJECT> instanciate(){
		instance = newInstance(getAClass());
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	protected Collection getListeners(){
		return null;
	}
	
	public abstract OBJECT build();
	
	/**/
	
	public static interface Listener<OBJECT> {
		
		/**/
		
		public static class Adapter<OBJECT> extends BeanAdapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			/**/
			
			public static class Default<OBJECT> extends Listener.Adapter<OBJECT> implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
	}
	
}
