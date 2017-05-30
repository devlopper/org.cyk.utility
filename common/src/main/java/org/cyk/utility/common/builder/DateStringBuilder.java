package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.Constant;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class DateStringBuilder extends AbstractStringBuilder implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	private Date date;
	private Constant.Date.Part part = Constant.Date.Part.DATE_AND_TIME;
	private Constant.Date.Length length = Constant.Date.Length.SHORT;
	
	@Override
	protected String buildWhenBlank() {
		Date date = getDate();
		if(date==null)
			return Constant.EMPTY_STRING;
		return new SimpleDateFormat(Constant.Date.getPattern(getLocale(),getPart(), getLength()).getValue(),getLocale()).format(date);
	}
	
	public DateStringBuilder setDate(Integer dayOfMonth,Integer monthOfYear,Integer year,Integer hourOfDay,Integer minuteOfHour,Integer millisOfSecond){
		setDate(new DateBuilder().setDate(dayOfMonth, monthOfYear, year, hourOfDay, minuteOfHour, millisOfSecond).build());
		return this;
	}
	
	@Override
	protected Collection<Listener> getListeners() {
		return Listener.COLLECTION;
	}
		
	/**/
	
	/**/
	
	public static interface Listener extends AbstractStringBuilder.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractStringBuilder.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
}