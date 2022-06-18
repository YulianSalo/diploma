FROM ubuntu:20.04

RUN apt update && apt upgrade -y
RUN apt install git python3 python3-pip -y

###
ADD https://github.com/YulianSalo/diploma/archive/refs/heads/master.zip .

RUN git clone https://github.com/YulianSalo/diploma.git

###
COPY ./version.txt ./diploma

RUN pip install pipreqs

WORKDIR /diploma

RUN pipreqs --encoding utf-8 "./"

RUN pip install -r requirements.txt

ENTRYPOINT [ "bash" ]
