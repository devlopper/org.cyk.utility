package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.helper.ValidationHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.hibernate.validator.constraints.Email;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ValidationHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
    public void electronicMailFormat() {
    	ElectronicMail electronicMail = new ElectronicMail().setMailValue("a..@mail.com");
    	ValidationHelper.Validate validate = new ValidationHelper.Validate.Adapter.Default(electronicMail);
    	Collection<String> messages = validate.execute();
    	System.out.println(messages);
    }
    
    /**/
    
    @Getter @Setter @Accessors(chain=true)
    public static class ElectronicMail implements Serializable {
    	private static final long serialVersionUID = 1L;
    	
    	@Email @NotNull
		private String mailValue;
    	
    }
	
	
}
