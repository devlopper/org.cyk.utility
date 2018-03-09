package org.cyk.utility.common;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.common.helper.ValidationHelper;
import org.cyk.utility.common.model.Identifiable;
import org.cyk.utility.common.test.Runnable;
import org.cyk.utility.common.test.TestHelper;
import org.cyk.utility.common.test.Try;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.hibernate.validator.constraints.Email;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ValidationHelperUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
		
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.testmsg", StringHelper.class.getClassLoader());
	}
	
	@Test
    public void manyConstraints() {
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().validate(new ManyConstraints()),
				"F1 : ne peut pas être nul","##__field__.f.2## : ne peut pas être nul"
				,"##__field__.f.3## : ne peut pas être nul","##__field__.f.4## : ne peut pas être nul");
	}
	
	@Test
    public void manyConstraintsThrowMessages() {
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {
				ValidationHelper.getInstance().validate(new ManyConstraints(),Boolean.TRUE);
			}
		}).setExpectedThrowableClass(ThrowableHelper.ThrowableMarkerCompileTime.class)
			.setExpectedThrowableMessage("1 F1 : ne peut pas être nul"+Constant.LINE_DELIMITER+"2 ##__field__.f.2## : ne peut pas être nul"
				+Constant.LINE_DELIMITER+"3 ##__field__.f.3## : ne peut pas être nul"+Constant.LINE_DELIMITER+"4 ##__field__.f.4## : ne peut pas être nul")
		.execute();
	}
	
	@Test
    public void manyConstraintsOtherOrder() {
		
	}
	
	@Test
    public void electronicMailFormatMessage() {
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().validate(new ElectronicMail("a..@mail.com"))
    			,"Adresse : a..@mail.com n'est pas une adresse de courrier électronique bien formée");
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().getValidateBuilder(new ElectronicMail("a..@mail.com"))
				.setIsFieldNameIncludedInMessage(Boolean.FALSE).execute(), "a..@mail.com n'est pas une adresse de courrier électronique bien formée");
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().getValidateBuilder(new ElectronicMail("a..@mail.com"))
				.setIsFieldNameIncludedInMessage(Boolean.TRUE).execute(), "Adresse : a..@mail.com n'est pas une adresse de courrier électronique bien formée");
    }

	@Test
	public void electronicMailFormatThrowMessage() {
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;
			@Override protected void __run__() throws Throwable {
				ValidationHelper.getInstance().validate(new ElectronicMail("a..@mail.com"),Boolean.TRUE);
			}
		}).setExpectedThrowableClass(ThrowableHelper.ThrowableMarkerCompileTime.class).execute();
    	
    }
	
	@Test
    public void electronicMailFormatMessageCustom() {
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().validate(new ElectronicMailCustomMessage("a..@mail.com"))
				, "Adresse : ##pas vraiment bon##");
    }
	
	@Test
    public void validateSystemIdentifier() {
		FieldHelper.Field.get(MyEntity.class, "code").getConstraints().setIsNullable(Boolean.TRUE);
		
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().validate(new MyEntity().setIdentifier("123"),Constant.Action.CREATE)
				, "La valeur(123) de l'attribut <<identifiant>> de l'entité <<mon entité>> doit être nulle.");
    }
	
	@Test
    public void validateFieldCustomNotNullable() {
		FieldHelper.Field.get(MyEntity.class, "code").getConstraints().setIsNullable(Boolean.FALSE);
		
		TestHelper.getInstance().assertCollection(ValidationHelper.getInstance().validate(new MyEntity(),Constant.Action.CREATE)
				,"La valeur de l'attribut <<code>> de l'entité <<mon entité>> doit être non nulle.");
    }
	
    
    /**/
    
    @Getter @Setter @Accessors(chain=true)
    public static class ElectronicMail implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@Email @NotNull
		private String address;
    	
    	public ElectronicMail() {}
    	
    	public ElectronicMail(String address) {
    		this.address = address;
    	}
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class ElectronicMailCustomMessage implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@Email(message="{pas vraiment bon}") @NotNull
		private String address;
    	
    	public ElectronicMailCustomMessage() {}
    	
    	public ElectronicMailCustomMessage(String address) {
    		this.address = address;
    	}
    }
	
    @Getter @Setter @Accessors(chain=true)
    public static class ManyConstraints implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@NotNull private String f1;
    	@NotNull private String f2;
    	@NotNull private String f3;
    	@NotNull private String f4;
    	
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class ManyConstraintsOtherOrder implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@NotNull private String f1;
    	@NotNull private String f3;
    	@NotNull private String f2;
    	@NotNull private String f5;
    	@NotNull private String f4;
    	
    }

    @Getter @Setter @Accessors(chain=true)
    public static class MyEntity implements Identifiable<String>, Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	private String identifier;
    	private String code;
    	private String name;
    	
    }
    
}
