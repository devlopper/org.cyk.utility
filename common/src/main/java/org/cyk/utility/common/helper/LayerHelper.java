package org.cyk.utility.common.helper;

import java.io.Serializable;

public class LayerHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static LayerHelper INSTANCE;
	
	public static LayerHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new LayerHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static enum Type{
		VIEW
		,CONTROLLER
		,BUSINESS
		,PERSISTENCE
		;
		public static Type DEFAULT = BUSINESS;
	}
}
