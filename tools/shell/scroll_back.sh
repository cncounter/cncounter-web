#!/bin/sh
# chmod 766 scroll_back.sh

# ENV
appname=cncounter-web
deploybase=/usr/local/www/cncounter.com/webapps
tomcatbase=/usr/local/www/tomcat-8.0.48

curtime=`date +%Y%m%d%H%M%S`
publicip=47.52.227.120

JAVA_OPTS=" -server -Xmx1024m -Xmn640m -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:$tomcatbase/logs/gc.log -XX:+PrintClassHistogram -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$tomcatbase/logs/tomcat.cnc.$curtime.hprof -Djava.rmi.server.hostname=$publicip -Dcom.sun.management.jmxremote.port=11001 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

# stop_and_publish
# stop
cd $deploybase/
$tomcatbase/bin/shutdown.sh 
# wait
sleep 5;

# force kill
TOMCAT_ID=`ps aux |grep "java"|grep "[D]catalina.base=$tomcatbase"|awk '{ print $2}'`

if [ -n "$TOMCAT_ID" ] ; then
    kill -9 "$TOMCAT_ID"
else
    echo "Tomcat has stoped"
fi

# clean
rm $tomcatbase/work/* -rf
rm $tomcatbase/temp/* -rf

# back logs
mkdir -p $tomcatbase/logs/bak
mv $tomcatbase/logs/catalina.out $tomcatbase/logs/bak/catalina.out.$curtime
mv $tomcatbase/logs/gc.log $tomcatbase/logs/bak/gc.log.$curtime

# copy war

mkdir -p $deploybase/bak 
rm -rf $deploybase/$appname
cp -ra $deploybase/bak/$appname $deploybase/$appname

rm -f $deploybase/$appname.war
cp -a $deploybase/bak/$appname.war $deploybase/$appname.war

# export env
export JAVA_OPTS=$JAVA_OPTS

# backend nohup start
nohup $tomcatbase/bin/startup.sh &

# check log
sleep 1;
tail -n 500 -f $tomcatbase/logs/catalina.out

