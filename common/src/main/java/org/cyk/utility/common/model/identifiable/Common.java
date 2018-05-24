package org.cyk.utility.common.model.identifiable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.computation.Trigger;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.instance.FieldValueComputationTriggers;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Common implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String COLUMN_NAME_UNKEYWORD = "the_";
	public static final String COLUMN_NAME_WORD_SEPARATOR = "_";
	public static final int COLUMN_VALUE_PRECISION = 30;
	public static final int COEFFICIENT_PRECISION = 5;
	public static final int PERCENT_PRECISION = 5;
	public static final int FLOAT_SCALE = 2;
	public static final int PERCENT_SCALE = 4;
	public static final BigDecimal LOWEST_NON_ZERO_POSITIVE_VALUE = new BigDecimal("0."+StringUtils.repeat('0', 10)+"1");
	
	@javax.persistence.Transient protected java.util.Map<String, Boolean> fieldValueComputedByUserMap;
	@javax.persistence.Transient protected FieldValueComputationTriggers fieldValueComputationTriggers;
	
	@javax.persistence.Transient protected LoggingHelper.Message.Builder loggingMessageBuilder;
	@javax.persistence.Transient protected String lastComputedLogMessage;
	
	/**/
	
	public Common register(Trigger trigger,Collection<String> fieldNames){
		if(CollectionHelper.getInstance().isNotEmpty(fieldNames)){
			if(fieldValueComputationTriggers == null)
				fieldValueComputationTriggers = new FieldValueComputationTriggers();
			fieldValueComputationTriggers.register(trigger, fieldNames);
		}
		
		return this;
	}
	
	public Common register(Trigger trigger,String...fieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(fieldNames))
			register(trigger, Arrays.asList(fieldNames));
		return this;
	}
	
	public Common register(Trigger trigger,ClassHelper.Listener.FieldName fieldName){
		if(fieldName != null)
			register(trigger, fieldName.getByValueUsageType(ClassHelper.Listener.FieldName.ValueUsageType.BUSINESS));
		return this;
	}
	
	public Trigger getTrigger(Collection<String> fieldNames){
		if(fieldValueComputationTriggers == null)
			return null;
		return fieldValueComputationTriggers.getTrigger(fieldNames);
	}
	
	public Trigger getTrigger(String...fieldNames){
		if(ArrayHelper.getInstance().isNotEmpty(fieldNames))
			return getTrigger(Arrays.asList(fieldNames));
		return null;
	}
	
	public Trigger getTrigger(ClassHelper.Listener.FieldName fieldName){
		return getTrigger(fieldName.getByValueUsageType(ClassHelper.Listener.FieldName.ValueUsageType.BUSINESS));
	}
	
	public Common __setFieldValueComputedByUser__(String name,Boolean value){
		if(StringHelper.getInstance().isNotBlank(name)){
			if(fieldValueComputedByUserMap == null){
				fieldValueComputedByUserMap = new HashMap<>();
			}
			fieldValueComputedByUserMap.put(name, value);
		}
		return this;
	}
	
	public Common __setBirthDateComputedByUser__(Boolean value){
		//return __setFieldValueComputedByUser__(FieldHelper.getInstance()
		//		.buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE), value);
		
		return __setFieldValueComputedByUser__(ClassHelper.getInstance().getFieldName(getClass(), ClassHelper.Listener.FieldName.BIRTH_DATE), value);
		//System.out.println("AbstractModelElement.__setBirthDateComputedByUser__() : "+ClassHelper.getInstance().getFieldName(getClass(), ClassHelper.Listener.FieldName.BIRTH_DATE));
		//return __setFieldValueComputedByUser__("globalIdentifier.existencePeriod.fromDate", value);
	}
	
	public Boolean isFieldValueComputedByUser(String name){
		if(fieldValueComputedByUserMap == null)
			return null;
		return fieldValueComputedByUserMap.get(name);
	}
	
	public Boolean isBirthDateComputedByUser(){
		return isFieldValueComputedByUser(ClassHelper.getInstance().getFieldName(getClass(), ClassHelper.Listener.FieldName.BIRTH_DATE));
	}
	
	public Boolean isDeathDateComputedByUser(){
		return isFieldValueComputedByUser(ClassHelper.getInstance().getFieldName(getClass(), ClassHelper.Listener.FieldName.DEATH_DATE));
	}
	
	public LoggingHelper.Message.Builder getLoggingMessageBuilder(Boolean createIfNull){
		if(loggingMessageBuilder == null && Boolean.TRUE.equals(createIfNull))
			loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default();
		return loggingMessageBuilder;
	}
	
	public String getUiString(){
		return null;
	}
 
	public String getLogMessage(){
		return CommonUtils.getInstance().getFieldsValues(this, Common.class);
	}
	
	public void computeLogMessage(){
		lastComputedLogMessage = getLogMessage();
	}
	
	protected <T> T getFromCode(Class<T> aClass,String code){
		return InstanceHelper.getInstance().getByIdentifier(aClass, code, ClassHelper.Listener.IdentifierType.BUSINESS);
	}
	
	protected <T extends Number> T getNumberFromObject(Class<T> aClass,Object value){
		return NumberHelper.getInstance().get(aClass, value, null);
	}
	
	protected <T> T instanciateOne(Class<T> aClass){
		return ClassHelper.getInstance().instanciateOne(aClass);
	}
	
	protected <T> T readFieldValue(String fieldName,Class<T> fieldValueClass,Boolean instanciateIfValueIsNull){
		return FieldHelper.getInstance().read(this, fieldValueClass, instanciateIfValueIsNull, fieldName);
	}
	
	protected <T> T readFieldValue(String fieldName,Boolean instanciateIfValueIsNull){
		return FieldHelper.getInstance().read(this, instanciateIfValueIsNull, fieldName);
	}
	
	public static String generateColumnName(String fieldName){
		return fieldName;
	}
	
	@Override
	public String toString() {
		return StringHelper.getInstance().convert(this);
	}
	
	/**/
	
	public Common computeChanges(){
		InstanceHelper.getInstance().computeChanges(this);
		return this;
	}
}
