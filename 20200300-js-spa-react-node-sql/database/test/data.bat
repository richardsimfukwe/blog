SET PGVERSION=12
SET PGDATABASE=projectname
SET PGUSER=projectname
SET PGPASSWORD=projectname

SET SCRIPT=data.sql
"C:\Program Files\PostgreSQL\%PGVERSION%\bin\psql.exe" -U %PGUSER% -f %~dp0%SCRIPT% %PGDATABASE%