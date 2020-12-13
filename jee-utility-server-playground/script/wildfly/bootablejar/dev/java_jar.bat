call set_oracle_variable.bat
call set_keycloak_variable.bat
call mvn -f ../../../.. clean package -Dwildfly.bootable.hollow=true -P package.war,package.wildfly.bootable.jaxrs.oracle,openapi.ui,prod
call java -jar ..\..\..\..\target\ROOT-bootable.jar --deployment=..\..\..\..\target\ROOT.war -Djboss.http.port=8081