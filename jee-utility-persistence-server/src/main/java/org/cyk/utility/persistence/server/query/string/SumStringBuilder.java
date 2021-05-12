package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface SumStringBuilder {

	String build(Sum sum);
	String build(Collection<String> strings);
	String build(String...strings);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements SumStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Sum sum) {
			if(sum == null)
				throw new RuntimeException("Sum is required");
			Collection<String> sums = sum.getStrings();
			String string = __build__(sums);
			return string;
		}
		
		private String __build__(Collection<String> sums) {
			String sum =  StringHelper.concatenate(sums, "+");
			return String.format(FORMAT, sum);
		}
		
		@Override
		public String build(Collection<String> strings) {
			if(CollectionHelper.isEmpty(strings))
				return null;
			return build(new Sum().add(strings));
		}
		
		@Override
		public String build(String... strings) {
			if(ArrayHelper.isEmpty(strings))
				return null;
			return build(CollectionHelper.listOf(strings));
		}
		
		private static final String FORMAT = "SUM(%s)";
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Sum extends org.cyk.utility.__kernel__.string.List implements Serializable {
		
		private Boolean a;
		
		@Override
		public Sum add(Collection<String> sums) {
			return (Sum) super.add(sums);
		}
		
		@Override
		public Sum add(String... sums) {
			return (Sum) super.add(sums);
		}
	}
	
	/**/
	
	static SumStringBuilder getInstance() {
		return Helper.getInstance(SumStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}