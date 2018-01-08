#!/bin/sh
# chmod 766 tomcat_restart.sh

# Exit the script if an error happens
# set -e

# ENV
tomcatbase=/usr/local/www/tomcat-8.0.48
curtime=`date +%Y%m%d%H%M%S`
publicip=46.186.88.158

JAVA_OPTS=" -Xmx1024m -Xmn640m -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:$tomcatbase/logs/gc.log -Djava.rmi.server.hostname=$publicip -Dcom.sun.management.jmxremote.port=11009 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"


# stop
$tomcatbase/bin/shutdown.sh

# clean
rm $tomcatbase/work/* -rf
rm $tomcatbase/temp/* -rf

# wait
sleep 5;

# force kill
TOMCAT_ID=`ps aux |grep "java"|grep "[D]catalina.base=$tomcatbase"|awk '{ print $2}'`

if [ -n "$TOMCAT_ID" ] ; then
    kill -9 "$TOMCAT_ID"
else
    echo "Tomcat has stoped"
fi

# back logs
mkdir -p $tomcatbase/logs/bak

mv $tomcatbase/logs/catalina.out $tomcatbase/logs/bak/catalina.out.curtime
mv $tomcatbase/logs/gc.log $tomcatbase/logs/bak/gc.log.curtime

# export env
export JAVA_OPTS=$JAVA_OPTS

# backend nohup start
nohup $tomcatbase/bin/startup.sh &

# check log
sleep 1;
tail -n 500 -f $tomcatbase/logs/catalina.out


