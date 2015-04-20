package org.cyk.utility.common.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DefaultColumn extends AbstractColumn<String, DefaultCell, String> implements Serializable {

	private static final long serialVersionUID = 8311076255598465773L;

}
