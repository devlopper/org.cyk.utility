package org.cyk.utility.__kernel__.__entities__.domain.budget;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Action.TABLE_NAME)
public class Action extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_PROGRAM) private Program program;
	
	@Transient private Section section;
	
	public Action(String code,String name,String programCode) {
		super(code,name);
		setProgramFromCode(programCode);
	}
	
	@Override
	public Action setIdentifier(String identifier) {
		return (Action) super.setIdentifier(identifier);
	}
	
	@Override
	public Action setCode(String code) {
		return (Action) super.setCode(code);
	}
	
	@Override
	public Action setName(String name) {
		return (Action) super.setName(name);
	}
	
	public Action setProgramFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.program = null;
		else
			this.program = InstanceGetter.getInstance().getByBusinessIdentifier(Program.class, code);
		return this;
	}
	
	public static final String FIELD_PROGRAM = "program";
	public static final String FIELD_SECTION = "section";
	
	public static final String COLUMN_PROGRAM = FIELD_PROGRAM;
	
	public static final String TABLE_NAME = "action";	
}
