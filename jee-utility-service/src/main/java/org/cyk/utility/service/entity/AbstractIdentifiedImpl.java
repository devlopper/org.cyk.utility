package org.cyk.utility.service.entity;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.Identified;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@JsonInclude(Include.NON_NULL)
public abstract class AbstractIdentifiedImpl<IDENTIFIER> extends AbstractObject implements Identified<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;

}