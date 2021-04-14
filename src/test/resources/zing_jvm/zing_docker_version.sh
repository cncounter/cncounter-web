# build docker image
echo docker build -t zing-docker-v13 .

#
echo print zing version
docker run -it --rm  --env ZING_LICENSE="`cat ./ZING_LICENSE`" zing-docker-v13 /opt/zing/zing-jdk13/bin/java -version

#
echo temp enter zing docker
docker run -it --rm  --env ZING_LICENSE="`cat ./ZING_LICENSE`" zing-docker-v13
