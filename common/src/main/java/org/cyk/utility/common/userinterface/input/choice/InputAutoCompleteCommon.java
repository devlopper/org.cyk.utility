package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.common.cdi.AbstractBean;
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
	
	public List<?> complete(final String query) {
		FilterHelper.Filter<Object> filter = new FilterHelper.Filter<>();
		filter.instanciateCriteria(Criteria.String.class).set(query);
		return (List<Object>) InstanceHelper.getInstance().get(clazz, filter, null);
	}
	
}
