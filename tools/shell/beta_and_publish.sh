#!/bin/sh
# beta_and_publish.sh
# chmod 766 beta_and_publish.sh


# ENV
srcurl=https://github.com/cncounter/cncounter-web.git
srcbranch=develop
srcpath=/usr/local/www/beta.cncounter/git_source
appname=cncounter-web
deploybase=/usr/local/www/cncounter.com/webapps
tomcatbase=/usr/local/www/tomcat-8.0.48

curtime=`date +%Y%m%d%H%M%S`
#curtime=$(date +%Y%m%d%H%M%S)
publicip=47.52.227.120

# add MVN_PATH
export PATH=/usr/local/apache-maven-3.3.9/bin:$PATH
# add JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk.x86_64


JAVA_OPTS="-server -XX:+PrintClassHistogram -XX:+HeapDumpOnOutOfMemoryError -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:$tomcatbase/logs/gc.log"


# init
mkdir -p $srcpath 
cd $srcpath 
git clone -b $srcbranch $srcurl $appname 


# package
cd $srcpath/$appname 
git checkout $srcbranch 
git pull 

cd $srcpath/$appname 
mvn clean package -P beta -U -DskipTests 

if [ ! $? -eq 0 ]
then
    echo "Error in mvn clean package!!! Stop deployment!" 
    exit 1
fi


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

mv $tomcatbase/logs/catalina.out $tomcatbase/logs/bak/catalina.out.curtime
mv $tomcatbase/logs/gc.log $tomcatbase/logs/bak/gc.log.curtime


# copy war

mkdir -p $deploybase/bak 
rm -rf $deploybase/bak/$appname
mv $deploybase/$appname $deploybase/bak/$appname

rm -f $deploybase/bak/$appname.war
mv $deploybase/$appname.war $deploybase/bak/$appname.war

cp $srcpath/$appname/target/$appname.war $deploybase/

# unzip war
cd $deploybase/
unzip $appname.war -d $appname


# export env
export JAVA_OPTS=$JAVA_OPTS

# backend nohup start
nohup $tomcatbase/bin/startup.sh &

# check log
sleep 1;
tail -n 500 -f $tomcatbase/logs/catalina.out

