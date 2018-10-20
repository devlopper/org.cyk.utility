package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.view.View;
import org.cyk.utility.client.controller.view.ViewBuilder;
import org.cyk.utility.client.controller.view.ViewTypeForm;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Named @ViewScoped @Getter @Setter
public class ViewPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private View view01;
	private View viewForm01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		view01 = createView01();
	}
	
	private View createView01() {
		MyData myData = new MyData();
		myData.setFirstName("Yao");
		InputStringLineOneBuilder inputStringLineOneBuilder;
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.setType(__inject__(ViewTypeForm.class));
		viewBuilder.setNameOutputPropertyValue("Mon titre de formulaire");
		inputStringLineOneBuilder = viewBuilder.addInputStringLineOneBuilder(Boolean.TRUE,"Firstname");
		inputStringLineOneBuilder.setObject(myData);
		inputStringLineOneBuilder.getFieldNameStrings(Boolean.TRUE).add("firstName");
		
		viewBuilder.addInputStringLineOneBuilder(Boolean.FALSE,"Lastnames");
		viewBuilder.addInputStringLineManyBuilder(Boolean.TRUE,"Other details");
		viewBuilder.setSubmitRunnable(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Submit");
				System.out.println("Input Values are : ");
				for(VisibleComponent index : view01.getVisibleComponents().get())
					if(index instanceof Input<?>) {
						((Input<?>)index).setFieldValueFromValue();
						System.out.println( ((Input<?>)index).getLabel().getValue() +" : "+ ((Input<?>)index).getValue() );
					}
				System.out.println("----------------- MyData ------------------");
				System.out.println(myData);
			}
		});
		viewBuilder.setCloseRunnable(new Runnable() {	
			@Override
			public void run() {
				System.out.println("Close");
			}
		});
		return viewBuilder.execute().getOutput();
	}
	
	/**/
	
	@Getter @Setter @ToString
	public static class MyData {
		
		private String firstName;
		private String lastNames;
		private String otherDetails;
	}
}
