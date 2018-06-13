package org.cyk.utility.constant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.string.StringConstant;

public interface Constant {
	
	
	String[] EMPTY_STRING_ARRAY = new String[]{};
	
	Collection<Long> COLLECTION_ONE_LONG_MIN_VALUE = Arrays.asList(Long.MIN_VALUE);

	BigDecimal NUMBER_LOWEST_NEGATIVE=new BigDecimal("-"+StringUtils.repeat('9', 18));
	BigDecimal NUMBER_HIGHEST_NEGATIVE_LOWER_THAN_ZERO=new BigDecimal("-0."+StringUtils.repeat('0', 18)+"1");
	
	BigDecimal NUMBER_LOWEST_POSITIVE_GREATER_THAN_ZERO=NUMBER_HIGHEST_NEGATIVE_LOWER_THAN_ZERO.negate();
	BigDecimal NUMBER_HIGHEST_POSITIVE=NUMBER_LOWEST_NEGATIVE.negate();
	
	BigDecimal BIGDECIMAL_100 = new BigDecimal("100");
	
	java.lang.Void VOID = null;
	
	/**/
	
	/**/
	
	public static enum Action {
	    LOGIN
	    ,LOGOUT
		,CREATE
	    ,READ
	    ,UPDATE
	    ,DELETE
	    ,SELECT
	    ,SEARCH
	    ,CONSULT
	    ,LIST
	    ,PRINT
	    
	    ;
		
		public static Boolean isCreateOrReadOrUpdateOrDeleteOrConsult(Object action){
			return isCreateOrReadOrUpdateOrDelete(action) || Action.CONSULT.equals(action);
		}
		
		public static Boolean isCreateOrReadOrUpdateOrDelete(Object action){
			return isCreateOrUpdateOrDelete(action) || Action.READ.equals(action);
		}
		
		public static Boolean isCreateOrUpdateOrDelete(Object action){
			return isCreateOrUpdate(action) || Action.DELETE.equals(action);
		}
		
		public static Boolean isCreateOrUpdate(Object action){
			return Action.CREATE.equals(action) || Action.UPDATE.equals(action);
		}
		
		public static Boolean isReadOrConsult(Object action){
			return isOneOf(action, Action.READ, Action.CONSULT);
		}
		
		public static Boolean isNotReadAndNotConsult(Object action){
			return isNotOneOf(action, Action.READ, Action.CONSULT);
		}
		
		public static Boolean isNotOneOf(Object action,Collection<Action> actions){
			return !Boolean.TRUE.equals(isOneOf(action, actions));
		}
		
		public static Boolean isNotOneOf(Object action,Action...actions){
			return !Boolean.TRUE.equals(isOneOf(action, actions));
		}
		
		public static Boolean isOneOf(Object action,Action...actions){
			return null;//ArrayHelper.getInstance().isNotEmpty(actions) && ArrayHelper.getInstance().isNotEmpty(actions) && isOneOf(action, Arrays.asList(actions));
		}
		
		public static Boolean isOneOf(Object action,Collection<Action> actions){
			return null;//CollectionHelper.getInstance().isNotEmpty(actions) && actions.contains(action);
		}
		
		/**/
		
		public static final Action[] SET_CRUD = {CREATE,READ,UPDATE,DELETE};
		public static final Action[] SET_CRUDC = {CREATE,READ,UPDATE,DELETE,CONSULT};
	}

	
	/**/
	
	public static class SimpleMailTransferProtocol implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static final String ADDRESS_FORMAT = "%s@%s.%s";
		public static final String SMTP = "smtp";
		public static final String PROPERTY_FORMAT = "mail."+SMTP+"%s.%s";
		
		public static String getProperty(String name,Boolean secured){
			return String.format(PROPERTY_FORMAT, Boolean.TRUE.equals(secured) ? "s" : StringConstant.EMPTY,name);
		}
		public static String getProperty(String name){
			return getProperty(name, Boolean.FALSE);
		}
		
		public static final String HOST = "host";
		public static final String FROM = "from";
		public static final String USER = "user";
		public static final String PASSWORD = "password";
		public static final String PORT = "port";
		public static final String AUTH = "auth";
		public static final String STARTTLS_ENABLE = "starttls.enable";
		public static final String SSL_ENABLE = "ssl.enable";
		
		public static final String PROPERTY_USERNAME = getProperty(USER);
		public static final String PROPERTY_PASSWORD = getProperty(PASSWORD);
		public static final String PROPERTY_FROM = getProperty(FROM);
		
		
	}

	/**/
	
	public static enum ApplicationType {
		STAND_ALONE
		,CLIENT_SERVER
		;
		
		public static ApplicationType DEFAULT = ApplicationType.CLIENT_SERVER;
	
	}

	/**/
	
	public static class Code implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static String SEPARATOR = StringConstant.EMPTY;
		
		public static String generateFromString(String string){
			return StringUtils.remove(string, CharacterConstant.SPACE);
		}
		
		public static String generate(Object[] objects,Object separator){
			Collection<String> collection = new ArrayList<>();
			for(Object object : objects)
				if(object instanceof Class<?>)
					collection.add(((Class<?>)object).getSimpleName().toUpperCase());
				else
					collection.add(object.toString());
			return StringUtils.join(collection,SEPARATOR);
		}
		
		public static String generate(Object[] objects){
			return generate(objects, SEPARATOR);
		}
		
		public static String generate(String masterCode,String detailCode,Object separator){
			return generate(new Object[]{masterCode,detailCode}, separator);
		}
		
		/*public static String getRelativeCode(AbstractCollection<?> collection,String code){
			return StringUtils.isBlank(collection.getItemCodeSeparator()) ? code : StringUtils.split(code,collection.getItemCodeSeparator())[1];
		}
		
		public static String getRelativeCode(AbstractCollectionItem<?> item){
			return getRelativeCode((AbstractCollection<?>) item.getCollection(), item.getCode());
		}*/
		
		public static String generateFieldNotNull(Class<?> aClass,String...fieldNames){
			return null;//generate(new Object[]{aClass,FieldHelper.getInstance().buildPath(fieldNames),NotNull.class});
		}
		
	}
}
