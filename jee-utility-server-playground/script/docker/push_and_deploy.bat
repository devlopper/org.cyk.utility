@echo off
For /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set mydate=%%c%%a%%b)
set mydate=%mydate: =0%

For /f "tokens=1-2 delims=/:" %%a in ("%TIME%") do (set mytime=%%a%%b)
set mytime=%mytime: =0%

SET timestamp=v0.0.0-%mydate%%mytime%
echo Publishing with time stamp : %timestamp% >> push_outputs.txt
docker tag mic-acteur-api 10.3.4.18:5000/mic-acteur-api:%timestamp% >> push_outputs.txt
docker push 10.3.4.18:5000/mic-acteur-api:%timestamp% >> push_outputs.txt
echo Deploying with time stamp : %timestamp% >> push_outputs.txt
curl -X PUT "http://10.3.4.17:30098/k5v/api/v1/test/deployments/mic-acteur-api" -H "accept: */*" -H "Content-Type: application/json" -d "{\"tag\":\"%timestamp%\"}"