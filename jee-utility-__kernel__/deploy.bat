mvn -N -P cyk.repo.repsy.io deploy:deploy-file -Dpackaging=jar -Dfile="target/jee-utility-__kernel__-0.1.0.jar" ^
-DrepositoryId=cyk.repo.repsy.io -Durl=https://repo.repsy.io/mvn/kycdev/default ^
-DgroupId=org.cyk.jee.utility.__kernel__ ^
-DartifactId=jee-utility-__kernel__ ^
-Dversion=0.1.0