package org.cyk.utility.__kernel__.__entities__;

import java.io.Serializable;

import javax.ws.rs.Path;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.representation.EntitySaver;
import org.cyk.utility.__kernel__.value.Value;

@Path(EmployeeSaver.PATH)
public interface EmployeeSaver extends EntitySaver<EmployeeDto> {

	/**/

	public static abstract class AbstractImpl extends EntitySaver.AbstractImpl<EmployeeDto> implements EmployeeSaver,Serializable {	
		@Override
		protected Class<EmployeeDto> getRepresentationEntityClass() {
			return EmployeeDto.class;
		}
	}
	
	/**/
	
	static EmployeeSaver getInstance() {
		return Helper.getInstance(EmployeeSaver.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	String PATH = "/cyk/entity/employee/saver";
	
}
