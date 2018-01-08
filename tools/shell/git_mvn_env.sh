
# may be need sudo ...

# git install
yum install git -y

# get mvn

mkdir -p /usr/local/download
cd /usr/local/download

wget http://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz

# unzip
cd /usr/local/download
tar zxf apache-maven-3.5.2-bin.tar.gz

# mv

cd /usr/local/download
mv apache-maven-3.5.2 /usr/local/tools/apache-maven-3.5.2


# add2path

mvnpathtip='# add MVN_PATH'
mvnpathscript='export PATH=/usr/local/tools/apache-maven-3.5.2/bin:$PATH'
echo $mvnpathtip >> /etc/profile
echo $mvnpathscript >> /etc/profile
source /etc/profile

