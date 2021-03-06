package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.persistence.server.query.Clause;
import org.cyk.utility.persistence.server.query.string.Replacer;
import org.cyk.utility.persistence.server.query.string.Replacer.Arguments;
import org.junit.jupiter.api.Test;

public class ReplacerUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void orderBy_code_desc(){
		assertThat(Replacer.getInstance().replace(new Arguments().setQueryValue("SELECT t FROM Tuple t ORDER BY t.code ASC")
				.setToken("ORDER BY t.code ASC").setReplacement("ORDER BY t.code DESC"))).isEqualTo("SELECT t FROM Tuple t ORDER BY t.code DESC");
		
		assertThat(Replacer.getInstance().replace(new Arguments().setQueryValue("SELECT t FROM Tuple t ORDER BY t.code ASC")
				.setToken("t.code ASC").setReplacement("t.code DESC"))).isEqualTo("SELECT t FROM Tuple t ORDER BY t.code DESC");
	}
	
	@Test
	public void orderBy_code_desc_clause(){
		assertThat(Replacer.getInstance().replace(new Arguments().setQueryValue("SELECT t FROM Tuple t ORDER BY t.code ASC").setClause(Clause.ORDER_BY)
				.setReplacement("t.code DESC"))).isEqualTo("SELECT t FROM Tuple t ORDER BY t.code DESC");
	}
	
	@Test
	public void orderBy_code_desc_clause_namedQuery(){
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.FALSE);
		QueryHelper.addQueries(new Query().setIdentifier("q1").setValue("SELECT t FROM Tuple t ORDER BY t.code ASC"));
		assertThat(Replacer.getInstance().replace(new Arguments().setQueryIdentifier("q1").setClause(Clause.ORDER_BY)
				.setReplacement("t.code DESC"))).isEqualTo("SELECT t FROM Tuple t ORDER BY t.code DESC");
	}
}