package org.cyk.utility.persistence.server.query.string;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.server.query.Clause;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface GroupStringBuilder {

	String build(Group group);	
	String build(Collection<String> groups);
	String build(String...groups);
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements GroupStringBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public String build(Group group) {
			if(group == null)
				throw new RuntimeException("Select group is required");
			if(CollectionHelper.isEmpty(group.getStrings()))
				throw new RuntimeException("Select group is required");
			Collection<String> groups = group.getStrings();
			String string = __build__(groups,group.clauseNameAppendable);
			return string;
		}
		
		private String __build__(Collection<String> orders,Boolean clauseNameAppendable) {
			String string =  StringHelper.concatenate(orders, ",");
			if(clauseNameAppendable == null || Boolean.TRUE.equals(clauseNameAppendable))
				return Clause.GROUP_BY.format(string);
			return string;
		}
		
		@Override
		public String build(Collection<String> groups) {
			if(CollectionHelper.isEmpty(groups))
				throw new RuntimeException("Select groups are required");
			return __build__(groups,Boolean.TRUE);
		}
		
		@Override
		public String build(String... groups) {
			if(ArrayHelper.isEmpty(groups))
				throw new RuntimeException("Select groups are required");
			return build(CollectionHelper.listOf(groups));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Group extends org.cyk.utility.__kernel__.string.List implements Serializable {
		
		private Boolean clauseNameAppendable;
		
		@Override
		public Group add(Collection<String> orders) {
			return (Group) super.add(orders);
		}
		
		@Override
		public Group add(String... orders) {
			return (Group) super.add(orders);
		}

	}
	
	/**/
	
	static GroupStringBuilder getInstance() {
		return Helper.getInstance(GroupStringBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}