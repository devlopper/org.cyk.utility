call mvn -f ../../pom.xml clean test
call mvn -f ../../pom_arquillian.xml clean verify -P package.wildfly.bootable.ejb.test