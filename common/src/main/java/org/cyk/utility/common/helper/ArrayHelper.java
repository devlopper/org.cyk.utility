package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Singleton
public class ArrayHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static ArrayHelper INSTANCE;
	
	public static ArrayHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ArrayHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Object[] reverse(Object[] objects){
		ArrayUtils.reverse(objects);
		return objects;
	}
	
	/**/
	
	@Getter @Setter @NoArgsConstructor
	public static class Element<INSTANCE> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Integer index;
		private INSTANCE instance;
		
		public Element(Integer index, INSTANCE instance) {
			super();
			this.index = index;
			this.instance = instance;
		}
		
		/**/
		
		@NoArgsConstructor
		public static class String extends Element<String> implements Serializable {
			private static final long serialVersionUID = 1L;

			public String(Integer index, String string) {
				super(index, string);
			}
			
		}
	}
}
