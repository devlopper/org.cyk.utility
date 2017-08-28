package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class DatabaseHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static DatabaseHelper INSTANCE;
	
	public static DatabaseHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new DatabaseHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static interface Source {
		
		String getDriverName();
		Source setDriverName(String driverName);
		
		String getDatabaseName();
		Source setDatabaseName(String databaseName);
		
		String getConnectionString();
		Source setConnectionString(String connectionString);
		
		Source open();
		Source close();
		
		Source execute(Collection<String> instructions);
		Source execute(String...instructions);
		
		@Getter
		public static class Adapter extends AbstractBean implements Source , Serializable {
			private static final long serialVersionUID = 1L;
			
			protected String driverName,databaseName,connectionString;
			
			@Override
			public Source setDriverName(String driverName) {
				return null;
			}
			
			@Override
			public Source setDatabaseName(String databaseName) {
				return null;
			}
			
			@Override
			public Source setConnectionString(String connectionString) {
				return null;
			}
			
			@Override
			public Source open() {
				return null;
			}
			
			@Override
			public Source close() {
				return null;
			}
			
			@Override
			public Source execute(Collection<String> instructions) {
				return null;
			}
			
			@Override
			public Source execute(String...instructions) {
				return null;
			}
			
			@Getter
			public static class Default extends Source.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Source setDriverName(String driverName) {
					this.driverName = driverName;
					return this;
				}
				
				@Override
				public Source setDatabaseName(String databaseName) {
					this.databaseName = databaseName;
					return this;
				}
				
				@Override
				public Source setConnectionString(String connectionString) {
					this.connectionString = connectionString;
					return this;
				}
			}
		}
		
		public static interface Server extends Source {
			
			String getHost();
			Server setHost(String host);
			
			Integer getPort();
			Server setPort(Integer port);
		
			@Getter
			public static class Adapter extends Source.Adapter.Default implements Server , Serializable {
				private static final long serialVersionUID = 1L;
				
				protected String host;
				protected Integer port;
				
				@Override
				public Server setHost(String host) {
					return null;
				}
				
				@Override
				public Server setPort(Integer port) {
					return null;
				}
				
				@Getter
				public static class Default extends Server.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;
					
					@Override
					public Server setHost(String host) {
						this.host = host;
						return this;
					}
					
					@Override
					public Server setPort(Integer port) {
						this.port = port;
						return this;
					}	
				}
			}
		
			public static interface MySql extends Server {
				
				public static class Adapter extends Server.Adapter.Default implements MySql,Serializable {
					private static final long serialVersionUID = 1L;
					
					public static class Default extends MySql.Adapter implements MySql,Serializable {
						private static final long serialVersionUID = 1L;

						@Override
						public String getConnectionString() {
							return "jdbc:mysql://"+getHost()+":"+getPort()+"/"+getDatabaseName();
						}		
					}				
				}	
			}
		}
	}
	
	
	
	public static interface Server {
		/*
		Mode getMode();
		Server setMode(Mode mode);
		
		String getConnectionUrl();
		*/
		Server execute(Source source,Collection<String> instructions);
		Server execute(Source source,String...instructions);
		
		@Getter
		public static class Adapter extends AbstractBean implements Server,Serializable {
			private static final long serialVersionUID = 1L;
			
			//protected Mode mode;
			protected String connectionUrl;
			
			@Override
			public Server execute(Source source,Collection<String> instructions) {
				return null;
			}

			@Override
			public Server execute(Source source,String... instructions) {
				return null;
			}
			
			public static class Default extends Server.Adapter implements Server,Serializable {
				private static final long serialVersionUID = 1L;

				
			}
		}
		
		/**/
		
		public static interface MySql extends Server {
		
			public static class Adapter extends Server.Adapter.Default implements MySql,Serializable {
				private static final long serialVersionUID = 1L;
				
				public static class Default extends MySql.Adapter implements MySql,Serializable {
					private static final long serialVersionUID = 1L;

					@Override
					public String getConnectionUrl() {
						return super.getConnectionUrl();
					}
					
									
				}				
			}	
		}
	
		/**/

	}
	
	
}
