package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Singleton;

import org.cyk.utility.common.helper.TimeHelper.Instant;

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
							Instant from = (Instant) getProperty(PROPERTY_NAME_INSTANT_1);
							Instant to = (Instant) getProperty(PROPERTY_NAME_INSTANT_2);
							Integer durationInMillisecond = getPropertyAsInteger(Builder.PROPERTY_NAME_PORTION_IN_MILLISECOND);
							Integer dayOfMonth = InstanceHelper.getInstance().getIfNotNullElseDefault(from.getDayOfMonth(), new Byte("1")).intValue();
							Date endDate = to.getDate();
							
							for(Short yearIndex = from.getYear() ; yearIndex <= to.getYear() ; yearIndex++){	
								for(Byte monthOfYearIndex = from.getMonthOfYear() ; monthOfYearIndex <= to.getMonthOfYear() ; monthOfYearIndex++){
									Set<Byte> dayIndexes = new LinkedHashSet<>();
									if(from.getDayOfWeek()==null){
										dayIndexes.add(from.getDayOfMonth());
									}else{
										Integer monthNumberOfDays = TimeHelper.getInstance().getNumberOfDaysOfMonth(monthOfYearIndex.intValue(), yearIndex.intValue());
										do{
											Integer dayOfWeek = TimeHelper.getInstance().getDayOfWeek(dayOfMonth, monthOfYearIndex.intValue(), yearIndex.intValue());
											if(from.getDayOfWeek().equals(dayOfWeek.byteValue()) || dayOfMonth==monthNumberOfDays){
												break;
											}
											dayOfMonth++;
										}while(true);
										for(Integer i = dayOfMonth ; i <= monthNumberOfDays ; i = i + TimeHelper.WEEK_NUMBER_OF_DAY)
											if(TimeHelper.getInstance().compare(yearIndex.intValue(), monthOfYearIndex.intValue(), i, endDate)<=0)
												dayIndexes.add(i.byteValue());
									}
									dayOfMonth = 1;
									for(Byte dayOfMonthIndex : dayIndexes){
										for(Byte hourOfDayIndex = from.getHourOfDay() ; hourOfDayIndex <= to.getHourOfDay() ; hourOfDayIndex++){	
											for(Byte minuteOfHourIndex = from.getMinuteOfHour() ; minuteOfHourIndex <= to.getMinuteOfHour() ; minuteOfHourIndex++){
												events.add(eventBuilder.setProperty(EventHelper.Event.Builder.PROPERTY_NAME, getProperty(PROPERTY_NAME))
													.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_FROM, new TimeHelper.Builder.Instant.Adapter
															.Default(new TimeHelper.Instant(yearIndex, monthOfYearIndex, dayOfMonthIndex, null, hourOfDayIndex
																	, minuteOfHourIndex, null, null)).execute())
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
