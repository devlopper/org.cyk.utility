package org.cyk.utility.__kernel__.persistence.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.persistence.query.QueryExecutor.Arguments;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestedEntityChild {

	@Id private String identifier;
	@NotNull @Column(unique = true) private String code;
	@NotNull private String name;
	
	@ManyToOne @NotNull private TestedEntityParent parent;
	
	public TestedEntityChild(String identifier, String code, String name,String parentCode) {
		super();
		this.identifier = identifier;
		this.code = code;
		this.name = name;
		setParentFromCode(parentCode);
	}
	
	public TestedEntityChild setParentFromCode(String code) {
		if(StringHelper.isBlank(code))
			parent = null;
		else
			parent = QueryExecutor.getInstance().executeReadOne(TestedEntityParent.class,new Arguments()
					.setQuery(QueryGetter.getInstance().getBySelectByBusinessIdentifiers(TestedEntityParent.class))
					.addFilterField("identifiers",code));
		return this;
	}
}
