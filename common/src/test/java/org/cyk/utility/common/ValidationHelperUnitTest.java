package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ValidationHelper;
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
		
	}
	
	@Test
    public void electronicMailFormatMessage() {
    	ElectronicMail electronicMail = new ElectronicMail().setAddress("a..@mail.com");
    	ValidationHelper.Validate validate = new ValidationHelper.Validate.Adapter.Default(electronicMail);
    	assertList(new ArrayList<>(validate.execute()), Arrays.asList("adresse Adresse email mal formée"));
    }
	
	@Test(expected=RuntimeException.class)
    public void electronicMailFormatThrowMessage() {
    	ElectronicMail electronicMail = new ElectronicMail().setAddress("a..@mail.com");
    	ValidationHelper.Validate validate = new ValidationHelper.Validate.Adapter.Default(electronicMail).setIsAutomaticallyThrowMessages(Boolean.TRUE);
    	validate.execute();
    }
	
	@Test
    public void electronicMailFormatMessageCustom() {
		ElectronicMailCustomMessage electronicMail = new ElectronicMailCustomMessage().setAddress("a..@mail.com");
    	ValidationHelper.Validate validate = new ValidationHelper.Validate.Adapter.Default(electronicMail);
    	assertList(new ArrayList<>(validate.execute()), Arrays.asList("adresse ##pas vraiment bon##"));
    }
    
    /**/
    
    @Getter @Setter @Accessors(chain=true)
    public static class ElectronicMail implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@Email @NotNull
		private String address;
    	
    }
    
    @Getter @Setter @Accessors(chain=true)
    public static class ElectronicMailCustomMessage implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@Email(message="{pas vraiment bon}") @NotNull
		private String address;
    	
    }
	
	
}
