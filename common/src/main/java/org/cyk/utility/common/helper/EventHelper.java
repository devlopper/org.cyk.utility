package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
		
		private static final String TO_STRING_FORMAT = "%s , %s (%s)";
		
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
			return String.format(TO_STRING_FORMAT, name,dates.execute(),duration.execute());
		}
		
		/**/
		
		public static interface Builder<OUTPUT> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {
			
			public static class Adapter<OUTPUT> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<OUTPUT> implements Builder<OUTPUT>,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
				
				/**/
				
				public static class Default<OUTPUT> extends Builder.Adapter<OUTPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<OUTPUT> outputClass) {
						super(outputClass);
					}
				}
			}
			
			/**/
			
			public static interface Property extends Builder<Event> {
				
				public static class Adapter extends Builder.Adapter.Default<Event> implements Property,Serializable {
					private static final long serialVersionUID = 1L;

					public Adapter() {
						super(Event.class);
					}
					
					/**/
					
					public static class Default extends Property.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;

						@Override
						protected Event __execute__() {
							Event event = new Event();
							event.setIdentifier(getPropertyAsNumber(Long.class, PROPERTY_NAME_IDENTIFIER));
							event.setName((String)getProperty(PROPERTY_NAME));
							event.setFrom((Date) getProperty(PROPERTY_NAME_FROM));
							event.setTo((Date) getProperty(PROPERTY_NAME_TO));
							event.setDurationInMillisecond(getPropertyAsNumber(Long.class, PROPERTY_NAME_DURATION_IN_MILLISECOND));
							
							if(event.getTo()==null){
								event.setTo(new Date(event.getFrom().getTime()+event.getDurationInMillisecond()));
							}else if(event.getDurationInMillisecond()==null)
								event.setDurationInMillisecond(event.getTo().getTime() - event.getFrom().getTime());
							
							return event;
						}
					}
				}			
			}
			
			public static interface Interval extends Builder<Collection<Event>> {
				
				public static class Adapter extends Builder.Adapter.Default<Collection<Event>> implements Interval,Serializable {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unchecked")
					public Adapter() {
						super((Class<Collection<Event>>) ClassHelper.getInstance().getByName(Collection.class));
					}
					
					/**/
					
					public static class Default extends Interval.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;

						@Override
						protected Collection<Event> __execute__() {
							Collection<Event> events = new ArrayList<Event>();
							
							EventHelper.Event.Builder.Property eventBuilder = new EventHelper.Event.Builder.Property.Adapter.Default();
							
							Integer year_1 = getPropertyAsInteger(PROPERTY_NAME_YEAR_1);
							Integer monthOfYear_1 = getPropertyAsInteger(PROPERTY_NAME_MONTHOFYEAR_1);
							Integer dayOfMonth_1 = getPropertyAsInteger(PROPERTY_NAME_DAYOFMONTH_1);
							Integer hourOfDay_1 = getPropertyAsInteger(PROPERTY_NAME_HOUROFDAY_1);
							Integer minuteOfHour_1 = getPropertyAsInteger(PROPERTY_NAME_MINUTEOFHOUR_1);
							
							Integer durationInMillisecond = getPropertyAsInteger(Builder.PROPERTY_NAME_DURATION_IN_MILLISECOND);
							
							Integer year_2 = getPropertyAsInteger(PROPERTY_NAME_YEAR_2);
							Integer monthOfYear_2 = getPropertyAsInteger(PROPERTY_NAME_MONTHOFYEAR_2);
							Integer dayOfMonth_2 = getPropertyAsInteger(PROPERTY_NAME_DAYOFMONTH_2);
							Integer hourOfDay_2 = getPropertyAsInteger(PROPERTY_NAME_HOUROFDAY_2);
							Integer minuteOfHour_2 = getPropertyAsInteger(PROPERTY_NAME_MINUTEOFHOUR_2);
							
							for(Integer yearIndex = year_1 ; yearIndex <= year_2 ; yearIndex++){
								for(Integer monthOfYearIndex = monthOfYear_1 ; monthOfYearIndex <= monthOfYear_2 ; monthOfYearIndex++){
									for(Integer dayOfMonthIndex = dayOfMonth_1 ; dayOfMonthIndex <= dayOfMonth_2 ; dayOfMonthIndex++){
										for(Integer hourOfDayIndex = hourOfDay_1 ; hourOfDayIndex <= hourOfDay_2 ; hourOfDayIndex++){	
											for(Integer minuteOfHourIndex = minuteOfHour_1 ; minuteOfHourIndex <= minuteOfHour_2 ; minuteOfHourIndex++){
												events.add(eventBuilder.setProperty(EventHelper.Event.Builder.PROPERTY_NAME, getProperty(PROPERTY_NAME))
													.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_FROM, new TimeHelper.Builder.Part.Adapter.Default()
													.setProperty(PROPERTY_NAME_YEAR, yearIndex).setProperty(PROPERTY_NAME_MONTHOFYEAR, monthOfYearIndex)
													.setProperty(PROPERTY_NAME_DAYOFMONTH, dayOfMonthIndex).setProperty(PROPERTY_NAME_HOUROFDAY, hourOfDayIndex)
													.setProperty(PROPERTY_NAME_MINUTEOFHOUR, minuteOfHourIndex).execute())
													.setProperty(PROPERTY_NAME_DURATION_IN_MILLISECOND, durationInMillisecond)
													.execute());
											}
										}
									}	
								}
							}
							
							return events;
						}
					}
				}			
			}
			
		}
		
	}
}
