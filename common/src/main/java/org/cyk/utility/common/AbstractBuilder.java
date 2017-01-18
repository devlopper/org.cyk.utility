package org.cyk.utility.common;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
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
			aClass = (Class<OBJECT>) inject(CommonUtils.class).getClassParameterAt(getClass(), 0);
		return aClass;
	}

	public AbstractBuilder<OBJECT> instanciate(){
		instance = newInstance(getAClass());
		return this;
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
