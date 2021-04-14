From ubuntu:18.04

ENV PATH="${PATH}:/opt/zing/zing-jdk13/bin"

RUN apt-get update && \
  apt-get install -y software-properties-common && \

# Copy Azul's public key
  apt-key adv --keyserver hkps://keyserver.ubuntu.com --recv-keys 0xB1998361219BD9C9 && \

# Complete the installation (on Debian-based systems)
  /usr/bin/apt-add-repository "deb https://repos.azul.com/zing/ubuntu $(lsb_release -sc) main" && \
  apt-get update -y && \
  apt-get install zing-jdk13.0.0 -y \

# https://docs.azul.com/zing/zing13-quick-start-container.htm
