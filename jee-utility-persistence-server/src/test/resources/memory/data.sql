DROP ALIAS IF EXISTS myproc;
CREATE ALIAS myproc FOR "org.cyk.utility.persistence.server.DataType.myStoredProcedure";

Insert into DATATYPE (identifier,code,name) values ('dt1','SG','String');
Insert into DATATYPE (identifier,code,name,description) values ('dt2','INT','Integer','Valeur entière');
Insert into DATATYPE (identifier,code,name) values ('dt3','LG','Long');
Insert into DATATYPE (identifier,code,name) values ('dt4','ST','Short');