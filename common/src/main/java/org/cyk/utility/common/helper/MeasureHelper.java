package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.common.Constant;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

public class MeasureHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	private static MeasureHelper INSTANCE;
	
	public static MeasureHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new MeasureHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(of={"nameIdentifier"})
	@ToString(of={"nameIdentifier"})
	public static class Type implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected String nameIdentifier;
		protected String defaultUnitNameIdentifier;
		protected List<Unit> units = new ArrayList<>();
		
		public Type add(String name,Long value){
			Unit unit = new Unit(this, name, value);
			add(unit);
			return this;
		}
		
		public Type add(Unit...units){
			if(units!=null)
				for(Unit unit : units)
					this.units.add(unit);
			return this;
		}
		
		public Unit getNextUnit(Unit unit){
			for(int i = 0 ; i < units.size() - 1 ; i++)
				if(units.get(i).equals(unit))
					return units.get(i+1);
			return null;
		}
		
		/**/
		
		public static final Type DISTANCE = new Type();
		public static final Type TIME = new Type();
		
		static {
			DISTANCE.nameIdentifier = "distance";
			//DISTANCE.units = new Unit[]{ new Unit(DISTANCE,"meter",1l) };
			
		}
		
		/**/
		
		public static class Time extends Type implements Serializable {
			private static final long serialVersionUID = 1L;
			
			public static final Time INSTANCE = new Time();
			public static Unit MILLISECOND;
			public static Unit SECOND;
			public static Unit MINUTE;
			public static Unit HOUR;
			public static Unit DAY;
			
			{
				
				nameIdentifier = "time";
				add(DAY = new Unit(this, "day",24 * 60 * 60 * 1000l),HOUR = new Unit(this, "hour",60 * 60 * 1000l),MINUTE = new Unit(this, "minute",60 * 1000l)
						,SECOND = new Unit(this, "second",1000l),MILLISECOND = new Unit(this, "millisecond",1l));
			}
			
			private Time(){}
			
		}
		
		/**/
		
		@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
		public static class Unit implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Type type;
			private String nameIdentifier;
			private Long value;
			
			/**/
			
			public static interface Stringifier extends org.cyk.utility.common.Builder.Stringifier<Long> {
				
				public static class Adapter extends org.cyk.utility.common.Builder.Stringifier.Adapter.Default<Long> implements Unit.Stringifier,Serializable {
					private static final long serialVersionUID = 1L;

					public Adapter(Long input) {
						super(Long.class, input);
					}
					
					public static class Default extends Unit.Stringifier.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;

						public Default(Long input) {
							super(input);
						}
						
						public Default(Integer input) {
							this(input.longValue());
						}
						
						@Override
						protected String __execute__() {
							Collection<String> strings = new ArrayList<>();
							Unit unit = (Unit) getProperty(PROPERTY_NAME_MEASURE_TYPE_UNIT);
							Long d = getInput(),q,r;
							do{
								q = d / unit.getValue();
								r = d % unit.getValue();
								if(q!=0)
									strings.add(String.valueOf(q)+Constant.CHARACTER_SPACE+new StringHelper.ToStringMapping.Adapter.Default().setInput(unit.getNameIdentifier()).setProperty(PROPERTY_NAME_PLURAL, q>1).execute());
								if(r==0)
									break;
								d = r;
								unit = unit.getType().getNextUnit(unit);
							}while(unit!=null);
							return CollectionHelper.getInstance().concatenate(strings, Constant.CHARACTER_COMA.toString());
						}
						
					}
				}
			}
			
		}
	}
	
}
