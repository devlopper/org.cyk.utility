package org.cyk.utility.common.computation;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Set of configurations for data reading. Supports lazy 
 * @author Komenan.Christian
 *
 */
@Getter @Setter @NoArgsConstructor
public class DataReadConfiguration implements Serializable {

	private static final long serialVersionUID = -8258556701704160443L;
	
	private Long firstResultIndex;
	private Long maximumResultCount;
	
	private String sortField;
	private Boolean ascendingOrder;
	private Map<String, Object> filters;
	private String globalFilter;
	
	public DataReadConfiguration(Long firstResultIndex, Long maximumResultCount, String sortField,
			Boolean ascendingOrder, Map<String, Object> filters, String globalFilter) {
		super();
		this.firstResultIndex = firstResultIndex;
		this.maximumResultCount = maximumResultCount;
		this.sortField = sortField;
		this.ascendingOrder = ascendingOrder;
		this.filters = filters;
		this.globalFilter = globalFilter;
	}
	
	private Boolean autoClear = Boolean.TRUE;
	
	{
		clear();
	}
	
	public DataReadConfiguration(DataReadConfiguration configuration) {
		set(configuration);
	}
	
	public DataReadConfiguration(Long firstResultIndex, Long maximumResultCount) {
		super();
		this.firstResultIndex = firstResultIndex;
		this.maximumResultCount = maximumResultCount;
	}
	
	public void set(DataReadConfiguration configuration) {
		firstResultIndex = configuration.firstResultIndex;
		maximumResultCount = configuration.maximumResultCount;
	}
	
	public void clear(){
		firstResultIndex = null;
		maximumResultCount =  null;
	}

	

}
