DROP ALIAS IF EXISTS myproc;
CREATE ALIAS myproc FOR "org.cyk.utility.persistence.server.hibernate.DataType.myStoredProcedure";

DROP ALIAS IF EXISTS AP_ACTUALIZE_MV;
CREATE ALIAS AP_ACTUALIZE_MV FOR "org.cyk.utility.persistence.server.hibernate.DataType.AP_ACTUALIZE_MV";

Insert into DATATYPE (identifier,code,name) values ('dt1','SG','String');
Insert into DATATYPE (identifier,code,name) values ('dt2','INT','Integer');
Insert into DATATYPE (identifier,code,name) values ('dt3','LG','Long');
Insert into DATATYPE (identifier,code,name) values ('dt4','ST','Short');