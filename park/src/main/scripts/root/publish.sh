#!/bin/bash

parentdir=$(cd $(dirname $0); pwd)
parentdir=${parentdir##*/}

proj=${parentdir}           # "usedcar-crm-pm-core"
proj_env=$1                 # "uat"
proj_filename="${proj}-${proj_env}.tar.gz"
now="`date +%Y%m%d%H%M%S`"
ftp_url="http://192.168.145.11/CRM/zip"

cd /data1/services/${proj}/
mkdir -p ./bak
if [ -f "./${proj_filename}" ];then
  cd bak
  count=$(ls|wc -l)
  if [[ ${count} -gt 10 ]]; then
    ls -lrt -d * | awk '{print $9}' | head -10 | xargs rm -rf
  fi
  cd /data1/services/${proj}/
  mkdir ./bak/${now}
  cp ./${proj_filename} ./bak/${now}/
fi
# wget -N ${ftp_url}/${proj_filename}
rz -y
rm -rf /data1/services/${proj}/lib
tar -zxvf ./${proj_filename}
# wget -N ${ftp_url}/publish.sh
(find . -name "*.sh" | xargs sed -i 's/\r$//g')
./${proj}.jar.sh restart
