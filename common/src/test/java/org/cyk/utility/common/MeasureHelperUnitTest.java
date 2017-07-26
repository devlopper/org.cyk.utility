package org.cyk.utility.common;

import org.cyk.utility.common.helper.MeasureHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MeasureHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void stringfy(){
		assertNull(new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(0).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.SECOND).execute());
		assertEquals("0 seconde", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(0).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.SECOND).setProperty(Action.PROPERTY_NAME_SKIP_ZERO, Boolean.FALSE).execute());
		assertEquals("1 seconde", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(1000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.SECOND).execute());
		assertEquals("2 secondes", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(2000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.SECOND).execute());
		
		assertEquals("1 minute", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(60000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.MINUTE).execute());
		
		assertEquals("1 minute,1 seconde", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(61000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.MINUTE).execute());
		
		assertEquals("1 minute,59 secondes", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(119000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.MINUTE).execute());
		
		assertEquals("2 minutes", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(120000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.MINUTE).execute());
		
		assertEquals("2 minutes,1 seconde", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(121000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.MINUTE).execute());
		
		assertEquals("2 minutes,59 secondes", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(179000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.MINUTE).execute());
		
		assertEquals("1 heure", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(3600000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.HOUR).execute());
		
		assertEquals("1 heure,1 minute,1 seconde", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(3661000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.HOUR).execute());
		
		assertEquals("1 heure,1 minute,59 secondes", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(3719000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.HOUR).execute());
		
		assertEquals("2 heures,59 minutes,59 secondes", new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(10799000).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
				, MeasureHelper.Type.Time.HOUR).execute());
	}
	
}
