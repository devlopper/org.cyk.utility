mvn -N -P cyk.repo.repsy.io deploy:deploy-file -Dpackaging=jar -Dfile="target/jee-utility-client-controller-web-jsf-primefaces-0.1.0.jar" ^
-DrepositoryId=cyk.repo.repsy.io -Durl=https://repo.repsy.io/mvn/kycdev/default ^
-DgroupId=org.cyk.jee.utility.client.controller.web.jsf.primefaces ^
-DartifactId=jee-utility-client-controller-web-jsf-primefaces ^
-Dversion=0.1.0