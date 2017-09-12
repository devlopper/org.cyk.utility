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
		assertList(new ArrayList<>(new ValidationHelper.Validate.Adapter.Default(new ElectronicMail("a..@mail.com"))
    			.execute()), Arrays.asList("adresse : a..@mail.com n'est pas une adresse de courrier électronique bien formée"));
		assertList(new ArrayList<>(new ValidationHelper.Validate.Adapter.Default(new ElectronicMail("a..@mail.com")).setIsFieldNameIncludedInMessage(false)
    			.execute()), Arrays.asList("a..@mail.com n'est pas une adresse de courrier électronique bien formée"));
		assertList(new ArrayList<>(new ValidationHelper.Validate.Adapter.Default(new ElectronicMail("a..@mail.com")).setIsFieldNameIncludedInMessage(true)
    			.execute()), Arrays.asList("adresse : a..@mail.com n'est pas une adresse de courrier électronique bien formée"));
    }

	@Test(expected=RuntimeException.class)
    public void electronicMailFormatThrowMessage() {
    	new ValidationHelper.Validate.Adapter.Default(new ElectronicMail("a..@mail.com")).setIsThrowMessages(Boolean.TRUE).execute();
    }
	
	@Test
    public void electronicMailFormatMessageCustom() {
		assertList(new ArrayList<>(new ValidationHelper.Validate.Adapter.Default(new ElectronicMailCustomMessage("a..@mail.com")).execute())
				, Arrays.asList("adresse : ##pas vraiment bon##"));
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
	
	
}
