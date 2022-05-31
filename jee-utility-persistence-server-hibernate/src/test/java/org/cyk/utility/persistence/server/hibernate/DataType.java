package org.cyk.utility.persistence.server.hibernate;

import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.server.view.MaterializedViewManager;

@Entity
@NamedStoredProcedureQueries(value = {
	@NamedStoredProcedureQuery(name = "myproc", procedureName = "myproc")
	,@NamedStoredProcedureQuery(
			name = MaterializedViewManager.AbstractImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME
			,procedureName = MaterializedViewManager.AbstractImpl.STORED_PROCEDURE_QUERY_PROCEDURE_NAME
			,parameters = {
				@StoredProcedureParameter(name = MaterializedViewManager.AbstractImpl.STORED_PROCEDURE_QUERY_PARAMETER_NAME_TABLE , mode = ParameterMode.IN,type = String.class)
			}
		)
})
public class DataType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	public static Integer AP_ACTUALIZE_MV_CALL_COUNT = 0;
	
	public static String myStoredProcedure() {
		return null;
	}
	
	public static String AP_ACTUALIZE_MV(String tableName) {
		if(DataType.TABLE_NAME.equals(tableName))
			DataType.actualize();
		else if(LongRunActualizationDataType.TABLE_NAME.equals(tableName))
			LongRunActualizationDataType.actualize();
		else if(VeryLongRunActualizationDataType.TABLE_NAME.equals(tableName))
			VeryLongRunActualizationDataType.actualize();
		TimeHelper.pause(1000l * 1);
		return null;
	}

	public static final String TABLE_NAME = "T1";
	
	public static void actualize() {
		AP_ACTUALIZE_MV_CALL_COUNT++;
	}
}