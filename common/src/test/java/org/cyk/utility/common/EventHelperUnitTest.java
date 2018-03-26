package org.cyk.utility.common;

import java.util.Collection;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.EventHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class EventHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void buildFromProperties(){
		EventHelper.Event.Builder.Property builder = new EventHelper.Event.Builder.Property.Adapter.Default();
		EventHelper.Event event = builder.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_IDENTIFIER, "1507")
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME, "Mon évènement")
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_FROM, new TimeHelper.Builder.String.Adapter.Default("19/11/2015 7:30").execute())
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_DURATION_IN_MILLISECOND, 1000 * 60 * 5)
				.execute();
		
		//System.out.println(event);
		AssertionHelper.getInstance().assertEquals("Mon évènement", event.getName());
		AssertionHelper.getInstance().assertEquals(1000l * 60 * 5, event.getDurationInMillisecond());
		AssertionHelper.getInstance().assertEquals(new TimeHelper.Builder.String.Adapter.Default("19/11/2015 7:30").execute(), event.getFrom());
		AssertionHelper.getInstance().assertEquals(new TimeHelper.Builder.String.Adapter.Default("19/11/2015 7:35").execute(), event.getTo());
	}
	
	@Test
	public void buildFromInterval(){
		EventHelper.Event.Builder.Interval builder = new EventHelper.Event.Builder.Interval.Adapter.Default();
		Collection<EventHelper.Event> events = builder
			.setProperty(EventHelper.Event.Builder.PROPERTY_NAME, "Mon évènement")
			.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_INSTANT_INTERVAL, new TimeHelper.Instant.Interval(new TimeHelper.Instant(2000, 1, 3, 3, 8, 30, 0, 0)
					, new TimeHelper.Instant(2000, 3, 3, 3, 8, 30, 0, 0), null, 1000l * 60 * 5))
			//.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_PORTION_IN_MILLISECOND, 1000 * 60 * 5)
			//.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_INSTANT_1, new TimeHelper.Instant(2000, 1, 3, 3, 8, 30, 0, 0))
			//.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_INSTANT_2, new TimeHelper.Instant(2000, 3, 3, 3, 8, 30, 0, 0))
			
			.execute();
		
		//System.out.println(events);
		AssertionHelper.getInstance().assertEquals(9, events.size());
		
	}
	
}
