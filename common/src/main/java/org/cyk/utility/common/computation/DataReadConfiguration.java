package org.cyk.utility.common.computation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Set of configurations for data reading. Supports lazy 
 * @author Komenan.Christian
 *
 */
@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class DataReadConfiguration extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8258556701704160443L;
	
	private Long firstResultIndex;
	private Long maximumResultCount;
	
	private String sortField;
	private Boolean ascendingOrder;
	private Map<String, Object> filters;
	private String globalFilter;
	
	private Map<String, Object> hints;
	private Set<String> attributes;
	
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
		if(configuration==null){
			clear();
			return;
		}
		this.firstResultIndex = configuration.firstResultIndex;
		this.maximumResultCount = configuration.maximumResultCount;
		this.sortField = configuration.sortField;
		this.ascendingOrder = configuration.ascendingOrder;
		this.filters = configuration.filters == null ? null : new HashMap<>(configuration.filters);
		this.globalFilter = configuration.globalFilter;
	}
	
	public DataReadConfiguration setHint(String name,Object value){
		if(hints==null)
			hints = new HashMap<>();
		hints.put(name, value);
		return this;
	}
	
	public DataReadConfiguration addAttributes(Set<String> attributes){
		if(attributes!=null){
			if(this.attributes==null)
				this.attributes = new HashSet<>();
			this.attributes.addAll(attributes);
		}
		return this;
	}
	
	public DataReadConfiguration addAttributes(String...attributes){
		if(attributes!=null)
			addAttributes(new HashSet<String>(Arrays.asList(attributes)));
		return this;
	}
	
	public void clear(){
		this.firstResultIndex = null;
		this.maximumResultCount =  null;
		this.sortField = null;
		this.ascendingOrder = null;
		this.filters = null;
		this.globalFilter = null;
		this.hints=null;
		this.attributes=null;
	}

	@Override
	public String toString() {
		return firstResultIndex+" , "+maximumResultCount+" , "+sortField+" , "+ascendingOrder+" , "+globalFilter+" , "+filters;
	}

}
