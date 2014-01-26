%1
cd %2
mvn archetype:generate -DarchetypeGroupId=org.cyk.archetype -DarchetypeArtifactId=system -DarchetypeVersion=1.0.0 -DarchetypeCatalog=local -DsystemId=%3
exit