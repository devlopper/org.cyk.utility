package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.helper.StringHelper.Location;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractReflectionHelper<TYPE> extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**/
	
	public static interface Get<SOURCE,TYPE> extends Action<SOURCE, Collection<TYPE>> {
		
		Integer getModifiers(TYPE type);
		String getName(TYPE type);
		
		Boolean getRecursivable();
		Get<SOURCE,TYPE> setRecursivable(Boolean value);
		
		String getToken();
		Get<SOURCE,TYPE> setToken(String token);
		
		Location getTokenLocation();
		Get<SOURCE,TYPE> setTokenLocation(Location location);
		
		Set<Integer> getModifiers();
		Get<SOURCE,TYPE> setModifiers(Set<Integer> modifiers);
		Get<SOURCE,TYPE> addModifiers(Integer...modifiers);
		
		@Getter @Setter @Accessors(chain=true)
		public static class Adapter<SOURCE,TYPE> extends Action.Adapter.Default<SOURCE, Collection<TYPE>> implements Get<SOURCE,TYPE>,Serializable {
			private static final long serialVersionUID = 1L;

			protected Boolean recursivable = Boolean.TRUE;
			protected String token;
			protected Location tokenLocation;
			protected Set<Integer> modifiers = new HashSet<>();
			
			public Adapter(SOURCE input) {
				super("Get fields", null, input, null, null);
			}
			
			@Override
			public String getName(TYPE type) {
				return null;
			}
			
			@Override
			public Integer getModifiers(TYPE type) {
				return null;
			}
			
			@Override
			public Get<SOURCE,TYPE> setRecursivable(Boolean value) {
				this.recursivable = value;
				return this;
			}
			
			@Override
			public Get<SOURCE,TYPE> setToken(String token) {
				this.token = token;
				return this;
			}
			
			@Override
			public Get<SOURCE,TYPE> setTokenLocation(Location location) {
				this.tokenLocation = location;
				return this;
			}
			
			@Override
			public Get<SOURCE,TYPE> setModifiers(Set<Integer> modifiers) {
				this.modifiers = modifiers;
				return this;
			}
			
			@Override
			public Get<SOURCE,TYPE> addModifiers(Integer...modifiers) {
				for(Integer modifier : modifiers)
					getModifiers().add(modifier);
				return this;
			}
			
			/**/
			
			public static class Default<SOURCE,TYPE> extends Get.Adapter<SOURCE,TYPE> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(SOURCE input) {
					super(input);
				}
				
				@Override
				protected Collection<TYPE> __execute__() {
					setOutput(new ArrayList<TYPE>());
					return get(getInput());
				}
				
				protected Collection<TYPE> get(SOURCE source) {
					//super class fields first
					
					if(getRecursivable()==null || Boolean.TRUE.equals(getRecursivable())){
						SOURCE parent = getParent(source);
						if (parent != null) {
							get(parent);
						}
					}
					//declared class fields second
					int searchMods = 0x0;
					for (Integer modifier : getModifiers())
						searchMods |= 0x0 | modifier;
					
					for (TYPE type : getTypes(source)) {
						if(((getModifiers(type) & searchMods) == searchMods) &&  StringHelper.getInstance().isAtLocation(getName(type), getToken(), getTokenLocation())){
							getOutput().add(type);
						}
					}
					
					return getOutput();
				}
				
				protected SOURCE getParent(SOURCE source){
					return null;
				}
				
				protected Collection<TYPE> getTypes(SOURCE source){
					return null;
				}
				
				
				
			}
		
		}
		
	}
}
