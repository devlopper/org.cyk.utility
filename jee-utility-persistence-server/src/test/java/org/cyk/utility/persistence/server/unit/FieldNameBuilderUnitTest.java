package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cyk.utility.persistence.server.query.string.FieldNameBuilder.id;
import static org.cyk.utility.persistence.server.query.string.FieldNameBuilder.identifier;
import static org.cyk.utility.persistence.server.query.string.FieldNameBuilder.code;
import static org.cyk.utility.persistence.server.query.string.FieldNameBuilder.name;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FieldNameBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void identifier_(){
		assertThat(identifier()).isEqualTo("identifier");
	}
	
	@Test
	public void myfield_identifier(){
		assertThat(identifier("myfield")).isEqualTo("myfield.identifier");
	}
	
	@Test
	public void id_(){
		assertThat(id()).isEqualTo("identifier");
	}
	
	@Test
	public void myfield_id(){
		assertThat(id("myfield")).isEqualTo("myfield.identifier");
	}
	
	@Test
	public void code_(){
		assertThat(code()).isEqualTo("code");
	}
	
	@Test
	public void myfield_code(){
		assertThat(code("myfield")).isEqualTo("myfield.code");
	}
	
	@Test
	public void name_(){
		assertThat(name()).isEqualTo("name");
	}
	
	@Test
	public void myfield_name(){
		assertThat(name("myfield")).isEqualTo("myfield.name");
	}
}