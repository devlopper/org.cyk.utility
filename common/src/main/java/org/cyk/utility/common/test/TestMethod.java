package org.cyk.utility.common.test;

import java.io.Serializable;

import org.cyk.utility.common.AbstractMethod;

public abstract class TestMethod extends AbstractMethod<Object, Object> implements Serializable{

		private static final long serialVersionUID = -134768081191734365L;

		@Override
		protected final Object __execute__(Object parameter) {
			test();
			return null;
		}
		
		protected abstract void test();
		
	}