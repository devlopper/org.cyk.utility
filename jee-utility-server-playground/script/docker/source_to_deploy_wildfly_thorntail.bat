cd ..
cd maven
call install.bat
call package_wildfly_thorntail.bat

cd..
cd docker
call build_wildfly_thorntail.bat
call push_and_deploy.bat