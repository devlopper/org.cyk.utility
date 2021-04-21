package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.PersistenceHelper;
import static org.cyk.utility.persistence.query.Language.jpql;
import org.cyk.utility.persistence.query.QueryParameterNameBuilder;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryStringBuilder {

	String build(Arguments arguments);	
	String buildSelectWhereFieldEquals(Class<?> tupleClass,String fieldName,String parameterName);	
	String buildSelectWhereFieldEquals(Class<?> tupleClass,String fieldName);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements QueryStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Arguments arguments) {
			SelectStringBuilder.Projection projection = arguments.projection;
			if(projection == null) {
				projection = new SelectStringBuilder.Projection().add("t");
			}
			
			FromStringBuilder.Tuple tuple = arguments.tuple;
			if(tuple == null) {
				if(arguments.tupleClass != null)
					tuple = new FromStringBuilder.Tuple(PersistenceHelper.getEntityName(arguments.tupleClass), "t");
			}
			
			WhereStringBuilder.Predicate predicate = arguments.predicate;
			
			OrderStringBuilder.Order order = arguments.order;
			
			GroupStringBuilder.Group group = arguments.group;
			
			return jpql(
					SelectStringBuilder.getInstance().build(projection)
					,FromStringBuilder.getInstance().build(tuple)
					,predicate == null ? null : WhereStringBuilder.getInstance().build(predicate)
					,order == null ? null : OrderStringBuilder.getInstance().build(order)
					,group == null ? null : GroupStringBuilder.getInstance().build(group)
					);
		}
		
		@Override
		public String buildSelectWhereFieldEquals(Class<?> tupleClass,String fieldName,String parameterName) {
			Field field = FieldHelper.getByName(tupleClass, fieldName);
			if(field == null)
				throw new RuntimeException(String.format("we cannot select where field equals because tuple class <<%s>> does not have field named <<%s>>",tupleClass,fieldName));
			if(StringHelper.isBlank(parameterName))
				parameterName = QueryParameterNameBuilder.getInstance().build(fieldName);
			return build(new Arguments().setTupleClass(tupleClass).setPredicate(new Predicate().add(String.format("t.%s = :%s", fieldName,parameterName))));
		}
		
		@Override
		public String buildSelectWhereFieldEquals(Class<?> tupleClass,String fieldName) {
			return buildSelectWhereFieldEquals(tupleClass, fieldName, null);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments {		
		private SelectStringBuilder.Projection projection;
		private FromStringBuilder.Tuple tuple;
		private WhereStringBuilder.Predicate predicate;
		private OrderStringBuilder.Order order;
		private GroupStringBuilder.Group group;
		
		private Class<?> tupleClass;
		
		public SelectStringBuilder.Projection getProjection(Boolean injectIfNull) {
			if(projection == null && Boolean.TRUE.equals(injectIfNull))
				projection = new SelectStringBuilder.Projection();
			return projection;			
		}
		
		public FromStringBuilder.Tuple getTuple(Boolean injectIfNull) {
			if(tuple == null && Boolean.TRUE.equals(injectIfNull))
				tuple = new FromStringBuilder.Tuple();
			return tuple;			
		}
		
		public WhereStringBuilder.Predicate getPredicate(Boolean injectIfNull) {
			if(predicate == null && Boolean.TRUE.equals(injectIfNull))
				predicate = new WhereStringBuilder.Predicate();
			return predicate;			
		}
		
		public OrderStringBuilder.Order getOrder(Boolean injectIfNull) {
			if(order == null && Boolean.TRUE.equals(injectIfNull))
				order = new OrderStringBuilder.Order();
			return order;			
		}
		
		public GroupStringBuilder.Group getGroup(Boolean injectIfNull) {
			if(group == null && Boolean.TRUE.equals(injectIfNull))
				group = new GroupStringBuilder.Group();
			return group;			
		}
	}
	
	/**/
	
	static QueryStringBuilder getInstance() {
		return Helper.getInstance(QueryStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}