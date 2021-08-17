package org.cyk.utility.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ValidatorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.class, Validator.class);
	}
	
	@Test
	public void validate() {
		assertValidate(Action.CREATE,List.of(new Data()),"year is required");
		assertValidate(Action.CREATE,List.of(new Data().setYear(0)),"year <<0>> is too low");
		assertValidate(Action.CREATE,List.of(new Data().setYear(9)),"year <<9>> is too low");
		assertValidate(Action.CREATE,List.of(new Data().setYear(10)));
		assertValidate(Action.CREATE,List.of(new Data().setYear(0),new Data().setYear(10),new Data(),new Data().setYear(9))
				,"year <<0>> is too low","year is required","year <<9>> is too low");
	}
	
	/**/
	
	private static void assertValidate(Action action,Collection<Data> datas,String...expectedMessages) {
		ThrowablesMessages throwablesMessages = Validator.getInstance().validate(Data.class,datas, action);
		if(ArrayHelper.isEmpty(expectedMessages))
			assertThat(throwablesMessages).isNull();
		else {
			assertThat(throwablesMessages).isNotNull();
			assertThat(throwablesMessages.getMessages()).containsExactly(expectedMessages);
		}
		
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Data {
		private Integer year;
	}
	
	/**/
	
	@org.cyk.utility.__kernel__.annotation.Test
	public static class ValidatorImplTest extends Validator.AbstractImpl implements Serializable {

		@Override
		protected <T> void __validate__(Class<T> klass, T entity, Object actionIdentifier,ThrowablesMessages throwablesMessages) {
			if(Data.class.equals(klass)) {
				throwablesMessages.addIfTrue("year is required", ((Data)entity).getYear() == null);
				throwablesMessages.addIfTrue(String.format("year <<%s>> is too low",((Data)entity).getYear()), NumberHelper.compare(((Data)entity).getYear(), 10, ComparisonOperator.LT));
			}
			super.__validate__(klass, entity, actionIdentifier, throwablesMessages);
		}
		
	}
}