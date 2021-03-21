package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;
import org.junit.jupiter.api.Test;

public class QueryStringBuilderUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void buildSelectWhereFieldEquals() { 	
    	assertThat(QueryStringBuilder.getInstance().buildSelectWhereFieldEquals(DataType.class, "code"))
    	.isEqualTo("SELECT t FROM DataType t WHERE t.code = :code");
	}
}