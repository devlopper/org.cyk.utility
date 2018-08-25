package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named @ViewScoped
public class MyPage implements Serializable {
	private static final long serialVersionUID = 1L;

	public void call() {
		System.out.println("MyPage.call() GREAT AGAIN 02");
	}
	
}
