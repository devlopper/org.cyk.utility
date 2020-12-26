package org.cyk.utility.client.controller.web;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.utility.__kernel__.object.AbstractObject;

public interface HttpServletListener {

	void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException;
	
	public static abstract class AbstractImpl extends AbstractObject implements HttpServletListener,Serializable {
		
		@Override
		public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {}
	}
}
