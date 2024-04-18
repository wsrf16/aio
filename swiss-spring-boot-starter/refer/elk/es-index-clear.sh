#!/bin/bash

#######################################################
# $Name:        es-index-clear.sh
# $Version:     v1.0
# $Function:    delete es index
# $Create Date: 2021-07-04
# $Description: shell
######################################################
if [ -z "$2" ] ;then
    echo "eg."
    echo "  es-index-clear.sh -30 -7"
    exit
fi

#start_date=`date -d "-7 days" +%Y.%m.%d`
#start_date=`date -d "-1 months" +%Y.%m.%d`
#start_date=`date -d "1 months ago" +%Y.%m.%d`


if [[ $2 -gt 0 ]]; then
  count=$2
else
  count=$((0-$2))
fi




for((i=0;i<=$count;i++));
do
  if [[ $2 -gt 0 ]]; then
    current=`date -d "$(($1+$i)) days" +%Y.%m.%d`
  else
    current=`date -d "$(($1-$i)) days" +%Y.%m.%d`
  fi

  curl -XDELETE http://127.0.0.1:9200/*-${current} -u elastic:Tianti@2019
  if [ $? -eq 0 ];then
    now=`date '+%Y-%m-%d %H:%M:%S'`
    echo $now"-->delete $current log success.." >> /tmp/es-index-clear.log
  else
    now=`date '+%Y-%m-%d %H:%M:%S'`
    echo $now"-->delete $current log fail.." >> /tmp/es-index-clear.log
  fi
done







