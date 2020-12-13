call mvn -f ../.. clean package -P package.war,package.thorntail,package.thorntail.jaxrs,jdbc.oracle,prod
REM swagger,deployment.server.standalone.thorntail.dev,security.single.sign.on.system.keycloak.dev
set SIIB_DB_HOST=localhost
set SIIB_DB_PORT=1521
set SIIB_DB_SID=XE
set SIIB_DB_USER=SIIBC_Actor
set SIIB_DB_PASSWORD=actor
call java -jar ..\..\target\actor-server-deployment-0.1.0-hollow-thorntail.jar ..\..\target\actor-server-deployment-0.1.0.war -s ..\..\target\actor-server-deployment-0.1.0\WEB-INF\classes\project-defaults.yml