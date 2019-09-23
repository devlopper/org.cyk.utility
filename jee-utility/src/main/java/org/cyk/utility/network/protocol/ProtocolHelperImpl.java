package org.cyk.utility.network.protocol;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class ProtocolHelperImpl extends AbstractHelper implements ProtocolHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<String,List<Integer>> SCHEME_PORTS = new HashMap<>();
	
	static {
		SCHEME_PORTS.put("http", Arrays.asList(80));
	}
	
	public static Collection<Integer> __getPorts__(String scheme) {
		return SCHEME_PORTS.get(scheme);
	}
	
	public static Integer __getDefaultPort__(String scheme) {
		return CollectionHelper.getFirst(__getPorts__(scheme));
	}
}
