package org.cyk.utility.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.string.AbstractStringFunctionImpl;

@Dependent
public class DurationStringBuilderImpl extends AbstractStringFunctionImpl implements DurationStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Duration duration;
	private DurationBuilder durationBuilder;
	
	@Override
	protected String __execute__() throws Exception {
		Duration duration = getDuration();
		if(duration == null) {
			DurationBuilder durationBuilder = getDurationBuilder();
			if(durationBuilder!=null)
				duration = durationBuilder.execute().getOutput();
		}
		Collection<String> strings = null;
		if(duration == null) {
			
		}else {
			strings = new ArrayList<>();	
			if(__inject__(NumberHelper.class).isGreaterThanZero(duration.getNumberOfMinute()))
				strings.add(duration.getNumberOfMinute()+" min");
			if(__inject__(NumberHelper.class).isGreaterThanZero(duration.getNumberOfSecond()))
				strings.add(duration.getNumberOfSecond()+" sec");
			if(__inject__(NumberHelper.class).isGreaterThanZero(duration.getNumberOfMillisecond()))
				strings.add(duration.getNumberOfMillisecond()+"");
		}
		String string = __injectStringHelper__().concatenate(strings,ConstantCharacter.COMA.toString());
		return string;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public DurationStringBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}
	
	@Override
	public DurationBuilder getDurationBuilder() {
		return durationBuilder;
	}
	
	@Override
	public DurationStringBuilder setDurationBuilder(DurationBuilder durationBuilder) {
		this.durationBuilder = durationBuilder;
		return this;
	}
	
	@Override
	public DurationBuilder getDurationBuilder(Boolean injectIfNull) {
		if(durationBuilder == null && Boolean.TRUE.equals(injectIfNull))
			durationBuilder = DependencyInjection.inject(DurationBuilder.class);
		return durationBuilder;
	}
	
	/**/
	
	public static final String FIELD_DURATION_BUILDER = "durationBuilder";
}
