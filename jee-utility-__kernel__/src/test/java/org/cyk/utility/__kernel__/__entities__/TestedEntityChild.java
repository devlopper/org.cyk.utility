package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryGetter;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestedEntityChild extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	@ManyToOne @NotNull private TestedEntityParent parent;
	
	public TestedEntityChild(String identifier, String code, String name,String parentCode) {
		super(code,name);
		this.identifier = identifier;
		setParentFromCode(parentCode);
	}
	
	@Override
	public String toString() {
		return identifier+" "+code+" "+name+":"+parent;
	}
	
	public TestedEntityChild setParentFromCode(String code) {
		if(StringHelper.isBlank(code))
			parent = null;
		else
			parent = QueryExecutor.getInstance().executeReadOne(TestedEntityParent.class,new QueryExecutorArguments()
					.setQuery(QueryGetter.getInstance().getBySelectByBusinessIdentifiers(TestedEntityParent.class))
					.addFilterField("identifiers",code));
		return this;
	}
}
