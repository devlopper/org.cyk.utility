package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false,doNotUseGetters = true)
public class QueryExecutorArgumentsDto extends AbstractObject implements Serializable {
	private String queryIdentifier;
	private FilterDto filter;
	private Integer firstTupleIndex;
	private Integer numberOfTuples;
	
	public FilterDto getFilter(Boolean injectIfNull) {
		if(filter == null && Boolean.TRUE.equals(injectIfNull))
			filter = new FilterDto();
		return filter;
	}
	
	public QueryExecutorArgumentsDto addFilterField(String fieldName, Object fieldValue) {
		getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
		return this;
	}
}