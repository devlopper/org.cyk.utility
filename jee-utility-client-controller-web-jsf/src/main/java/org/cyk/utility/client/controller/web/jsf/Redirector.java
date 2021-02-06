package org.cyk.utility.client.controller.web.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.PathAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.QueryAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Redirector {

	void redirect(Arguments arguments);
	
	void redirect(String url);
	
	void redirect(String outcome,Map<String,List<String>> parameters);
	
	public static abstract class AbstractImpl extends AbstractObject implements Redirector,Serializable {
		
		@Override
		public void redirect(Arguments arguments) {
			if(arguments == null)
				return;
			String url = arguments.url;
			if(StringHelper.isBlank(url))
				url = getUrl(arguments.outcome, arguments.parameters);
			if(arguments.processingShowable == null || Boolean.TRUE.equals(arguments.processingShowable))
				showProcessing();
			__redirect__(url);
		}
		
		protected void showProcessing() {
			
		}
		
		@Override
		public void redirect(String url) {
			redirect(new Arguments().setUrl(url));
		}
		
		@Override
		public void redirect(String outcome, Map<String, List<String>> parameters) {
			redirect(new Arguments().setOutcome(outcome).setParameters(parameters));
		}
		
		private void __redirect__(String url) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (IOException exception) {
				LogHelper.log(exception, getClass());
			}
		}
		
		/**/
		
		private String getUrl(String outcome,Map<String,List<String>> parameters) {
			if(StringHelper.isBlank(outcome))
				return null;
			NavigationCase navigationCase = NavigationCaseGetter.getInstance().get(outcome);
			if(navigationCase == null) {
				LogHelper.logSevere("No navigation case exist for outcome "+outcome, getClass());
				return null;
			}
			String path = navigationCase.getToViewId(FacesContext.getCurrentInstance());
			if(StringHelper.isBlank(path)) {
				LogHelper.logSevere("No path exist for outcome "+outcome, getClass());
				return null;
			}
			Collection<String> queries = new ArrayList<>();
			if(MapHelper.isNotEmpty(parameters)) {
				parameters.forEach( (name,values) -> {
					if(StringHelper.isNotBlank(name) && CollectionHelper.isNotEmpty(values))
						queries.add(name+"="+values.get(0));
				});
			}
			
			UniformResourceIdentifierAsFunctionParameter p = new UniformResourceIdentifierAsFunctionParameter();
			p.setRequest(FacesContext.getCurrentInstance().getExternalContext().getRequest());
			p.setPath(new PathAsFunctionParameter());
			p.getPath().setIdentifier(outcome);
			if(CollectionHelper.isNotEmpty(queries)) {
				p.setQuery(new QueryAsFunctionParameter());
				p.getQuery().setValue(CollectionHelper.isEmpty(queries) ? null : StringHelper.concatenate(queries, "&"));	
			}		
			return UniformResourceIdentifierHelper.build(p);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {
		private String outcome;
		private Map<String,List<String>> parameters;
		
		private String url;
		private Boolean processingShowable;
		
		private Listener listener;
		
		/**/
		
		public Map<String,List<String>> getParameters(Boolean injectIfNull) {
			if(parameters == null && Boolean.TRUE.equals(injectIfNull))
				parameters = new LinkedHashMap<>();
			return parameters;
		}
		
		public Arguments outcome(String outcome) {
			setOutcome(outcome);
			return this;
		}
		
		public Arguments parameters(Map<String,List<String>> parameters) {
			setParameters(parameters);
			return this;
		}
		
		public Arguments addParameters(Map<String,List<String>> parameters) {
			if(MapHelper.isEmpty(parameters))
				return this;
			getParameters(Boolean.TRUE).putAll(parameters);
			return this;
		}
		
		public Arguments addParameter(String name,String value) {
			if(StringHelper.isBlank(name) || StringHelper.isBlank(value))
				return this;
			addParameters(Map.of(name,List.of(value)));
			return this;
		}
	}
	
	public static interface Listener {
		void redirect(Arguments arguments);
	}
	
	/**/
	
	static Redirector getInstance() {
		return Helper.getInstance(Redirector.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();		
}