REM call set_oracle_variable.bat
REM call set_keycloak_variable.bat
mvn -f ../../../.. clean wildfly-jar:dev -P dev
REM mvn -f ../../../.. clean wildfly-jar:dev -P package.wildfly.bootable.jaxrs.oracle,dev