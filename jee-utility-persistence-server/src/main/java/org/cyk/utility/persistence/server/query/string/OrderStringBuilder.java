package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.server.query.Clause;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface OrderStringBuilder {

	String build(Order order);	
	String build(Collection<String> orders);
	String build(String...orders);
	String build(Map<String,SortOrder> orders);
	
	String buildFromTuple(String variableName,Collection<String> fieldsNames,SortOrder sortOrder);
	String buildFromTuple(String variableName,SortOrder sortOrder,String...fieldsNames);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements OrderStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Order order) {
			if(order == null)
				throw new RuntimeException("Select order is required");
			if(CollectionHelper.isEmpty(order.getStrings()))
				throw new RuntimeException("Select order is required");
			Collection<String> orders = order.getStrings();
			String string = __build__(orders,order.clauseNameAppendable);
			return string;
		}
		
		private String __build__(Collection<String> orders,Boolean clauseNameAppendable) {
			String string =  StringHelper.concatenate(orders, ",");
			if(clauseNameAppendable == null || Boolean.TRUE.equals(clauseNameAppendable))
				return Clause.ORDER_BY.format(string);
			return string;
		}
		
		@Override
		public String build(Collection<String> orders) {
			if(CollectionHelper.isEmpty(orders))
				throw new RuntimeException("Select orders are required");
			return __build__(orders,Boolean.TRUE);
		}
		
		@Override
		public String build(String... orders) {
			if(ArrayHelper.isEmpty(orders))
				throw new RuntimeException("Select orders are required");
			return build(CollectionHelper.listOf(orders));
		}
		
		@Override
		public String build(Map<String, SortOrder> orders) {
			if(MapHelper.isEmpty(orders))
				return null;
			Order order = new Order();
			order.add(orders);
			return build(order);
		}
		
		public String buildFromTuple(String variableName,Collection<String> fieldsNames,SortOrder sortOrder) {
			if(CollectionHelper.isEmpty(fieldsNames))
				throw new RuntimeException("Select fields names are required");
			return build(new Order().addFromTuple(variableName, fieldsNames,sortOrder));
		}
		
		@Override
		public String buildFromTuple(String variableName,SortOrder sortOrder, String... fieldsNames) {
			if(ArrayHelper.isEmpty(fieldsNames))
				throw new RuntimeException("Select fields names are required");
			return build(new Order().addFromTuple(variableName,sortOrder, fieldsNames));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Order extends org.cyk.utility.__kernel__.string.List implements Serializable {
		
		private Boolean clauseNameAppendable;
		
		@Override
		public Order add(Collection<String> orders) {
			return (Order) super.add(orders);
		}
		
		@Override
		public Order add(String... orders) {
			return (Order) super.add(orders);
		}
		
		public Order add(String fieldName,SortOrder sortOrder) {
			if(StringHelper.isBlank(fieldName))
				return this;
			if(sortOrder == null)
				sortOrder = SortOrder.ASCENDING;
			add(fieldName+" "+(SortOrder.DESCENDING.equals(sortOrder) ? "DESC" : "ASC"));
			return this;
		}
		
		public Order add(Map<String,SortOrder> orders) {
			if(MapHelper.isEmpty(orders))
				return this;
			orders.entrySet().forEach(entry -> add(entry.getKey(), entry.getValue()));
			return this;
		}
		
		public Order addFromTuple(String variableName,Collection<String> fieldsNames,SortOrder sortOrder) {
			if(StringHelper.isBlank(variableName) || CollectionHelper.isEmpty(fieldsNames))
				return this;
			if(sortOrder == null)
				sortOrder = SortOrder.ASCENDING;
			for(String fieldName : fieldsNames)
				add(variableName+"."+fieldName,sortOrder);
			return this;
		}
		
		public Order addFromTuple(String variableName,SortOrder sortOrder,String...fieldsNames) {
			if(StringHelper.isBlank(variableName) || ArrayHelper.isEmpty(fieldsNames))
				return this;
			return addFromTuple(variableName, CollectionHelper.listOf(fieldsNames),sortOrder);
		}
		
		public Order addFromTupleAscending(String variableName,String...fieldsNames) {
			return addFromTuple(variableName, SortOrder.ASCENDING, fieldsNames);
		}
		
		public Order addFromTupleDescending(String variableName,String...fieldsNames) {
			return addFromTuple(variableName, SortOrder.DESCENDING, fieldsNames);
		}
		
		public Order asc(String variableName,String...fieldsNames) {
			return addFromTupleAscending(variableName, fieldsNames);
		}
		
		public Order desc(String variableName,String...fieldsNames) {
			return addFromTupleDescending(variableName, fieldsNames);
		}
	}
	
	/**/
	
	static OrderStringBuilder getInstance() {
		return Helper.getInstance(OrderStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}