package org.cyk.utility.common.computation;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DataReadConfig implements Serializable {

	private static final long serialVersionUID = -8258556701704160443L;
	
	private Long firstResultIndex;
	private Long maximumResultCount;
	
	private Boolean autoClear = Boolean.TRUE;
	
	{
		clear();
	}
	
	public DataReadConfig(DataReadConfig dataReadConfig) {
		set(dataReadConfig);
	}
	
	public void set(DataReadConfig dataReadConfig) {
		firstResultIndex = dataReadConfig.firstResultIndex;
		maximumResultCount = dataReadConfig.maximumResultCount;
	}
	
	public void clear(){
		firstResultIndex = null;
		maximumResultCount =  null;
	}
}
