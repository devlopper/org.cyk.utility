package org.cyk.utility.__kernel__.klass;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class PropertyUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPersistable_null() {
		assertThat(Property.getProperty(null, Property.PERSISTABLE)).isNull();
	}
	
	@Test
	public void getPersistable_Persistable() {
		assertThat(Property.getProperty(Persistable.class, Property.PERSISTABLE)).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	public void getPersistable_NotPersistable() {
		assertThat(Property.getProperty(NotPersistable.class, Property.PERSISTABLE)).isEqualTo(Boolean.FALSE);
	}
	
	@Test
	public void getTupleName_null() {
		assertThat(Property.getProperty(null, Property.TUPLE_NAME)).isNull();
	}
	
	@Test
	public void getTupleName_Persistable() {
		assertThat(Property.getProperty(Persistable.class, Property.TUPLE_NAME)).isEqualTo("Persistable");
	}

	@Test
	public void getTransferable_null() {
		assertThat(Property.getProperty(null, Property.TRANSFERABLE)).isNull();
	}
	
	@Test
	public void getTransferable_Transferable() {
		assertThat(Property.getProperty(Transferable.class, Property.TRANSFERABLE)).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	public void getTransferable_NotTransferable() {
		assertThat(Property.getProperty(NotTransferable.class, Property.TRANSFERABLE)).isEqualTo(Boolean.FALSE);
	}
	
	/**/
	
	@Entity
	public static class Persistable {}
	
	public static class NotPersistable {}
	
	@XmlRootElement
	public static class Transferable {}
	
	public static class NotTransferable {}
}
