package org.cyk.utility.persistence.server.hibernate;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.time.TimeHelper;

@Entity
public class VeryLongRunActualizationDataType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	public static Integer AP_ACTUALIZE_MV_CALL_COUNT = 0;
	
	public static final String TABLE_NAME = "T3";
	
	public static void actualize() {
		TimeHelper.pause(1000l * 5);
		AP_ACTUALIZE_MV_CALL_COUNT++;
	}
}