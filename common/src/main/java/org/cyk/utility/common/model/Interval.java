package org.cyk.utility.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Interval extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Number from,to,distance;

	public Interval setFrom(Number from){
		this.from = from;
		distance = computeDistance();
		return this;
	}
	
	public Interval setTo(Number to){
		this.to = to;
		distance = computeDistance();
		return this;
	}
	
	private Number computeDistance(){
		if(this.from == null || this.to == null)
			return null;
		return new BigDecimal(to.doubleValue()).subtract(new BigDecimal(from.doubleValue())) ;
	}
	
	@Override
	public String toString() {
		return "["+from+","+to+"]";
	}
	
	public static final String FIELD_FROM = "from";
	public static final String FIELD_TO = "to";
	public static final String FIELD_DISTANCE = "distance";
	
	
	
}
