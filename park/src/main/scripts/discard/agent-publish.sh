#!/bin/bash

parentdir=$(cd $(dirname $0); pwd)
parentdir=${parentdir##*/}

proj=${parentdir}           # "usedcar-crm-pm-core"
proj_env=$1                 # "uat"
proj_filename="${proj}-${proj_env}.tar.gz"
ftp_url="http://192.168.145.11/CRM/zip"
deamon=$2
daemon_check_period=20
package_md5="package_md5"
package_compare="package_compare"

if [ "$proj_env" == "" ] && [ "$deamon" == "" ];then
  echo argument is empty
  exit 0
fi

if [ "$deamon" == "" ];then
  nohup $0 $proj_env deamon >/dev/null 2>&1 &
else
  while [ true ]; do
    cd /data1/services/${proj}/
    wget -O ${package_compare} ${ftp_url}/${proj_filename}

    md5_last=""
    md5_next=""
    if [ -f "./${package_md5}" ];then
      md5_last=$(cat ./${package_md5})
    fi
    echo "last:${md5_last}"
    md5_next=$(md5sum ./${package_compare}|cut -d ' ' -f1)
    echo ${md5_next}>package_md5
    echo "next:${md5_next}"

    if [ "${md5_next}" != "${md5_last}" ];then
      ./publish.sh ${proj_env}
    else
      echo no change happend.
    fi
    sleep $daemon_check_period
  done
fi
