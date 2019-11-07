package org.cyk.utility.__kernel__.object.__static__.persistence.embeddedable;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Embeddable
public class Existence extends AbstractObjectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name=COLUMN_CREATED_BY) @NotNull private String createdBy;
	@Column(name=COLUMN_CREATION_DATE) @NotNull private LocalDateTime creationDate;
	@Column(name=COLUMN_UPDATE_DATE) private LocalDateTime updateDate;
	
	/**/
	
	public static final String FIELD_CREATED_BY = "createdBy";
	public static final String FIELD_CREATION_DATE = "creationDate";
	public static final String FIELD_UPDATE_DATE = "updateDate";
	
	public static final String COLUMN_CREATED_BY = FIELD_CREATED_BY;
	public static final String COLUMN_CREATION_DATE = FIELD_CREATION_DATE;
	public static final String COLUMN_UPDATE_DATE = FIELD_UPDATE_DATE;
}
