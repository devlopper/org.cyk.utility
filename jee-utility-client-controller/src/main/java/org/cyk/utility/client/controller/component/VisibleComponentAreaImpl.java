package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class VisibleComponentAreaImpl extends AbstractObject implements VisibleComponentArea,Serializable{
	private static final long serialVersionUID = 1L;

	private VisibleComponentAreaDimension width,height;
	
	@Override
	public VisibleComponentAreaDimension getWidth() {
		return width;
	}

	@Override
	public VisibleComponentAreaDimension getWidth(Boolean injectIfNull) {
		return (VisibleComponentAreaDimension) __getInjectIfNull__(FIELD_WIDTH, injectIfNull);
	}

	@Override
	public VisibleComponentArea setWidth(VisibleComponentAreaDimension width) {
		this.width = width;
		return this;
	}

	@Override
	public VisibleComponentAreaDimension getHeight() {
		return height;
	}

	@Override
	public VisibleComponentAreaDimension getHeight(Boolean injectIfNull) {
		return (VisibleComponentAreaDimension) __getInjectIfNull__(FIELD_HEIGHT, injectIfNull);
	}

	@Override
	public VisibleComponentArea setHeight(VisibleComponentAreaDimension height) {
		this.height = height;
		return this;
	}

	public static final String FIELD_WIDTH = "width";
	public static final String FIELD_HEIGHT = "height";
	
}
