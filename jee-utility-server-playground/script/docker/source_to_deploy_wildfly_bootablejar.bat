cd ..
cd maven
call install.bat
call package_wildfly_bootablejar.bat

cd..
cd docker
call build_wildfly_bootablejar.bat
call push_and_deploy.bat