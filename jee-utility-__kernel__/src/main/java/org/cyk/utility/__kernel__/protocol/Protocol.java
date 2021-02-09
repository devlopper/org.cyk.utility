package org.cyk.utility.__kernel__.protocol;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public enum Protocol {

	HTTP(80)
	,HTTPS(80)
	;
	
	@Getter @Setter private String scheme;
	@Getter @Setter private List<Integer> ports;
	@Getter @Setter private Integer defaultPortIndex;
	
	private Protocol(String scheme,List<Integer> ports,Integer defaultPortIndex) {
		if(scheme == null || scheme.isBlank())
			scheme = name().toLowerCase();
		this.scheme = scheme;
		this.ports = ports;
		if(defaultPortIndex == null || defaultPortIndex < 0)
			defaultPortIndex = 0;
		this.defaultPortIndex = defaultPortIndex;
	}
	
	private Protocol(List<Integer> ports,Integer defaultPortIndex) {
		this(null,ports,defaultPortIndex);
	}
	
	private Protocol(Integer...ports) {
		this(null,List.of(ports),null);
	}
	
	public Integer getDefaultPort() {
		return ports.get(defaultPortIndex);
	}
}
