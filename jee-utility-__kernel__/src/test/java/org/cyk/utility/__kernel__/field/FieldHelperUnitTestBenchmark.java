package org.cyk.utility.__kernel__.field;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestBenchmark;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTestBenchmark extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Test
	public void readSystemIdentifier(){
		Class klass = new Class();
		execute(new Jobs().setName("Read system identifier").setNumberOfRound(100000000)
				.add("reflection generic : FieldHelper.read",new Runnable() {
				@Override
				public void run() {
					FieldHelper.read(klass,FieldName.IDENTIFIER,ValueUsageType.SYSTEM);
				}
			}).add("reflection specific  : FieldHelper.readSystemIdentifier",new Runnable() {
				@Override
				public void run() {
					FieldHelper.readSystemIdentifier(klass);
				}
			}).add("interface marker     : IdentifiableSystem.getSystemIdentifier", new Runnable() {
				@Override
				public void run() {
					((IdentifiableSystem<String>)klass).getSystemIdentifier();
				}
			})
		);
	}	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class implements IdentifiableSystem<String> {
		
		private String identifier;

		@Override
		public String getSystemIdentifier() {
			return identifier;
		}

		@Override
		public IdentifiableSystem<String> setSystemIdentifier(String identifier) {
			this.identifier = identifier;
			return this;
		}
	}
}
