package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.MetricsRepresentation;

@ApplicationScoped
public class MetricsRepresentationImpl extends AbstractObject implements MetricsRepresentation,Serializable {

	private Map<String,String> get(MetricsManager.Arguments arguments) {
		Map<String,Object> map = MetricsManager.getInstance().get(arguments);
		if(MapHelper.isEmpty(map))
			return null;
		TreeMap<String, String> result = new TreeMap<String, String>();
		map.forEach( (k,v) -> {
			result.put(k, v == null ? ConstantEmpty.STRING : v.toString());
		});
		return result;
	}
	
	@Override
	public Response get() {
		MetricsManager.Arguments arguments = null;
		Map<String,String> map = get(arguments);
		return Response.ok(map).build();
	}
	
	@Override
	public Boolean getEnabledAsBoolean() {
		return MetricsManager.getInstance().getEnabled();
	}
	
	@Override
	public Map<String, String> getAsMap() {
		MetricsManager.Arguments arguments = null;
		return get(arguments);
	}
	
	@Override
	public Response enable() {
		try {
			MetricsManager.getInstance().enable();
			return Response.ok().build();
		} catch (Exception exception) {
			return Response.serverError().entity(exception.getMessage()).build();
		}
	}
	
	@Override
	public Response disable() {
		try {
			MetricsManager.getInstance().disable();
			return Response.ok().build();
		} catch (Exception exception) {
			return Response.serverError().entity(exception.getMessage()).build();
		}
	}	
}