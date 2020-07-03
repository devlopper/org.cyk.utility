package org.cyk.utility.server.representation.api;
import java.io.Serializable;

import javax.ws.rs.Path;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.representation.EntitySaver;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.server.representation.entities.MyEntityDto;

@Path(MyEntitySaver.PATH)
public interface MyEntitySaver extends EntitySaver<MyEntityDto> {

	/**/

	public static abstract class AbstractImpl extends EntitySaver.AbstractImpl<MyEntityDto> implements MyEntitySaver,Serializable {	
		@Override
		protected Class<MyEntityDto> getRepresentationEntityClass() {
			return MyEntityDto.class;
		}
	}
	
	/**/
	
	static MyEntitySaver getInstance() {
		return Helper.getInstance(MyEntitySaver.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String PATH = "/cyk/entity/employee/saver";
	
}
