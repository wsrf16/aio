#!/bin/bash

parentdir=$(cd $(dirname $0); pwd)
parentdir=${parentdir##*/}

proj_name=${parentdir}           # "usedcar-crm-pm-core"
proj_env=$1                 # "uat"
proj_filename="${proj_name}-${proj_env}.tar.gz"
proj_basedir=~/app
proj_dir=${proj_basedir}/${proj_name}/
now="`date +%Y%m%d%H%M%S`"
ftp_url="http://192.168.145.11/CRM/zip"

cd ${proj_dir}
if [ -f "./${proj_filename}" ];then
  mkdir -p ./bak
  cd bak
  count=$(ls|wc -l)
  if [[ ${count} -gt 10 ]]; then
    ls -lrt -d * | awk '{print $9}' | head -10 | xargs rm -rf
  fi
  cd ${proj_dir}
  mkdir -p ./bak/${now}
  cp ./${proj_filename} ./bak/${now}/
else
  echo "${proj_filename} is not exist."
  # exit 0
fi
# wget -N ${ftp_url}/${proj_filename}
rz -y
if [[ $? -eq 0 ]]; then
  echo -e "\nupload succeed."
else
  echo -e "\nupload failed!"
  exit 0
fi
rm -rf ${proj_dir}/lib
tar -zxvf ./${proj_filename}
# wget -N ${ftp_url}/publish.sh
(find . -name "*.sh" | xargs sed -i 's/\r$//g')
./${proj_name}.jar.sh restartlog
