package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.CriteriaHelper.Criteria;
import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputAutoCompleteCommon extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<Object> clazz;
	protected FilterHelper.Filter<Object> filter = new FilterHelper.Filter<>();
	protected Criteria.String queryCriteria;
	protected DataReadConfiguration dataReadConfiguration;
	
	public InputAutoCompleteCommon() {
		queryCriteria = filter.instanciateCriteria(Criteria.String.class);
	}
	
	public List<?> complete(final String query) {
		return (List<Object>) InstanceHelper.getInstance().get(clazz, filter.use(query), dataReadConfiguration);
	}
	
	public Object getChoiceValue(Object choice){
		return InstanceHelper.getInstance().getIdentifier(choice);
	}
	
}
