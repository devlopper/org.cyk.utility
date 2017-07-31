package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class EventHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	private static EventHelper INSTANCE;
	
	public static EventHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new EventHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	@Getter @Setter
	public static class Event implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private static final String TO_STRING_FORMAT = "%s - %s , %s (%s)";
		
		private Long identifier;
		private String name;
		private Date from,to;
		private Long durationInMillisecond;
		
		@Override
		public String toString() {
			TimeHelper.Stringifier.Dates dates = new TimeHelper.Stringifier.Dates.Adapter.Default(new TimeHelper.Stringifier.Date[]{
					new TimeHelper.Stringifier.Date.Adapter.Default(from),new TimeHelper.Stringifier.Date.Adapter.Default(to)
			});
			TimeHelper.Stringifier.Duration duration = new TimeHelper.Stringifier.Duration.Adapter.Default(durationInMillisecond);
			return String.format(TO_STRING_FORMAT,identifier, name,dates.execute(),duration.execute());
		}
		
		/**/
		
		public static interface Builder extends org.cyk.utility.common.Builder.NullableInput<Event> {
			
			public static class Adapter extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<Event> implements Builder,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(Event.class);
				}
				
				/**/
				
				public static class Default extends Builder.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					@Override
					protected Event __execute__() {
						Event event = new Event();
						event.setIdentifier(getPropertyAsNumber(Long.class, PROPERTY_NAME_IDENTIFIER));
						event.setName((String)getProperty(PROPERTY_NAME));
						event.setFrom((Date) getProperty(PROPERTY_NAME_FROM));
						event.setTo((Date) getProperty(PROPERTY_NAME_TO));
						Long millisecond = getPropertyAsNumber(Long.class, PROPERTY_NAME_DURATION_IN_MILLISECOND);
						if(millisecond==null && event.getTo()!=null && event.getFrom()!=null)
							millisecond = event.getTo().getTime() - event.getFrom().getTime();
						event.setDurationInMillisecond(millisecond);
						return event;
					}
					
				}
			}
			
		}
		
	}
}
