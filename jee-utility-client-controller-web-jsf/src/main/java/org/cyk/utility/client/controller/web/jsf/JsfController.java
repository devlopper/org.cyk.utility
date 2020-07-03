package org.cyk.utility.client.controller.web.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.identifier.resource.PathAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.QueryAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

@ApplicationScoped
public class JsfController extends AbstractObject implements Serializable {

	public void redirect(String outcome,Map<String,List<String>> parameters) {
		if(StringHelper.isBlank(outcome))
			return;
		NavigationCase navigationCase = NavigationCaseGetter.getInstance().get(outcome);
		if(navigationCase == null) {
			LogHelper.logSevere("No navigation case exist for outcome "+outcome, getClass());
			return;
		}
		String path = navigationCase.getToViewId(FacesContext.getCurrentInstance());
		if(StringHelper.isBlank(path)) {
			LogHelper.logSevere("No path exist for outcome "+outcome, getClass());
			return;
		}
		Collection<String> queries = new ArrayList<>();
		if(MapHelper.isNotEmpty(parameters)) {
			parameters.forEach( (name,values) -> {
				if(StringHelper.isNotBlank(name) && CollectionHelper.isNotEmpty(values))
					queries.add(name+"="+values.get(0));
			});
		}
		UniformResourceIdentifierAsFunctionParameter uniformResourceIdentifierAsFunctionParameter = new UniformResourceIdentifierAsFunctionParameter();
		uniformResourceIdentifierAsFunctionParameter.setRequest(FacesContext.getCurrentInstance().getExternalContext().getRequest());
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).setValue(path);
		if(CollectionHelper.isNotEmpty(queries))
			uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).setValue(StringHelper.concatenate(queries, "&"));		
		String url = UniformResourceIdentifierHelper.build(uniformResourceIdentifierAsFunctionParameter);
		//System.out.println("JsfController.redirect() 1 : "+url);
		//Faces.redirect(url);
		
		UniformResourceIdentifierAsFunctionParameter p = new UniformResourceIdentifierAsFunctionParameter();
		p.setRequest(FacesContext.getCurrentInstance().getExternalContext().getRequest());
		p.setPath(new PathAsFunctionParameter());
		p.getPath().setIdentifier(outcome);
		if(CollectionHelper.isNotEmpty(queries)) {
			p.setQuery(new QueryAsFunctionParameter());
			p.getQuery().setValue(CollectionHelper.isEmpty(queries) ? null : StringHelper.concatenate(queries, "&"));	
		}		
		url = UniformResourceIdentifierHelper.build(p);
		//System.out.println("JsfController.redirect() 2 : "+url);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void redirect(String outcome) {
		redirect(outcome, null);
	}
	
	/**/
	
	public static JsfController getInstance() {
		return DependencyInjection.inject(JsfController.class);
	}
	
}
