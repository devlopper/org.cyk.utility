package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.persistence.server.query.string.OrderStringBuilder;
import org.cyk.utility.persistence.server.query.string.OrderStringBuilder.Order;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class OrderStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void order(){
		assertThat(OrderStringBuilder.getInstance().build(new Order().add("t.f ASC"))).isEqualTo("ORDER BY t.f ASC");
		assertThat(OrderStringBuilder.getInstance().build("t.a DESC")).isEqualTo("ORDER BY t.a DESC");
		assertThat(OrderStringBuilder.getInstance().build(new Order().add("t.f ASC","t.d ASC"))).isEqualTo("ORDER BY t.f ASC,t.d ASC");
		assertThat(OrderStringBuilder.getInstance().build(new Order().addFromTupleAscending("t","f").addFromTupleDescending("t","d"))).isEqualTo("ORDER BY t.f ASC,t.d DESC");
	}
	
	@Test
	public void order_map(){
		Map<String,SortOrder> map = new LinkedHashMap<>();
		map.put("t.f",SortOrder.DESCENDING);
		assertThat(OrderStringBuilder.getInstance().build(map)).isEqualTo("ORDER BY t.f DESC");
		
		map = new LinkedHashMap<>();
		map.put("t.f",SortOrder.DESCENDING);
		map.put("t.f2",SortOrder.ASCENDING);
		assertThat(OrderStringBuilder.getInstance().build(map)).isEqualTo("ORDER BY t.f DESC,t.f2 ASC");
	}
}