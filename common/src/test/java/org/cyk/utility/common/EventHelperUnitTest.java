package org.cyk.utility.common;

import java.util.Date;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.EventHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class EventHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		AssertionHelper.Listener.COLLECTION.add(new AssertionHelper.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;

			@Override
			public void assertEquals(String message, Object expected,Object actual) {
				Assert.assertEquals(message, expected, actual);
			}
		});
	}
	
	@Test
	public void build(){
		EventHelper.Event.Builder builder = new EventHelper.Event.Builder.Adapter.Default();
		EventHelper.Event event = builder.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_IDENTIFIER, "1507")
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME, "Mon évènement")
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_FROM, new TimeHelper.Builder.String.Adapter.Default("19/11/2015 7:30")
						.setProperty(EventHelper.Event.Builder.PROPERTY_FORMAT, "dd/MM/yyyy HH:mm").execute())
				.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_TO, new TimeHelper.Builder.String.Adapter.Default("19/11/2015 11:45")
						.setProperty(EventHelper.Event.Builder.PROPERTY_FORMAT, "dd/MM/yyyy HH:mm").execute())
				//.setProperty(EventHelper.Event.Builder.PROPERTY_NAME_DURATION_IN_MILLISECOND, 1000 * 60 * 5)
				.execute();
		
		System.out.println(event);
	}
	
	
}
