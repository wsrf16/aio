#!/bin/bash

function mkdirLog() {
  if [ ! -d $log_path ];then
    mkdir $log_path
  fi
}

function getIpAddr() {
  ip_addr=$(ip addr  |grep "eth0" |grep 'state UP' -A2 | tail -n1 | awk '{print $2}' | awk -F"/" '{print $1}')
  echo $ip_addr
}

function getExt() {
  _ext=${1##*.}
  echo $_ext
}

function getOnlyfilename() {
  _tmp=${1##*/}
  _onlyfilename=${tmp%.*}
  echo $_onlyfilename
}

function getFilename() {
  _filename=${1##*/}
  echo $_filename
}

function getDirectory() {
  echo ${1%/*}
}

function markProcess() {
  if [ $operate == "stop" ]; then
  # $shfilename   $sh_file_path
  #                         show line no   except         except         except
    process_line=$(ps -ef | grep -n "$1" | grep -v grep | grep -v kill | grep -v "$shfilename stop" | grep -v "$shfilename start" | grep -v "$shfilename status" | grep -v "$shfilename restart")
  else
    process_line=$(ps -ef | grep -n "$1" | grep -v grep | grep -v kill | grep -v "$shfilename calldaemon" | grep -v "$shfilename stop" | grep -v "$shfilename start" | grep -v "$shfilename status" | grep -v "$shfilename restart")
  fi

  echo $process_line >> $log_path/process.log
}

function getPid() {
  if [ $operate == "stop" ]; then
  # $shfilename   $sh_file_path
  #                         show line no   except         except         except
    process_line=$(ps -ef | grep -n "$1" | grep -v grep | grep -v kill | grep -v "$shfilename stop" | grep -v "$shfilename start" | grep -v "$shfilename status" | grep -v "$shfilename restart")
  else
    process_line=$(ps -ef | grep -n "$1" | grep -v grep | grep -v kill | grep -v "$shfilename calldaemon" | grep -v "$shfilename stop" | grep -v "$shfilename start" | grep -v "$shfilename status" | grep -v "$shfilename restart")
  fi

  #echo $process_line >> $log_path/process.log
  pid=($(echo $process_line | awk '{print $2}'))

  if [ "$pid" ]; then
    echo $pid
  else
    echo ''
  fi
}

function isRunning() {
  pid=$(getPid $file_path)
  if [ "$pid" ]; then
    echo true
  else
    echo ""
  fi
}

function run() {
  touch ${log_file_path}
  tail -n ${log_remain_line} ${log_file_path} > $log_path/tmp
  mv -f $log_path/tmp ${log_file_path}
  if [ $operate == "once" ]; then
    java $args -Dloader.path="$dir_path/lib/,$dir_path/conf/" -jar $1 2>&1 | tee ${log_file_path}
  elif [ $operate == "start" ]; then
    nohup java $args -Dloader.path="$dir_path/lib/,$dir_path/conf/" -jar $1 >>${log_file_path} 2>&1 &
  fi

  #nohup java $args -Dloader.path="$dir_path/lib/,$dir_path/conf/" -jar $1 >>${log_file_path} 2>&1 &
  #nohup java $args -Dloader.path="$dir_path/lib/,$dir_path/conf/" -jar $1 &

  sleep $check_period
  markProcess $1
}

function calldaemon() {
  echo "current running status..."
  while [ true ]; do
    is=$(isRunning $1)
    if [ ! ${is} ]; then
      #$sh_file_path start
      operate="start"
      run $1
      #nohup $sh_file_path start >/dev/null 2>&1 &
      #nohup $0 start >/dev/null 2>&1 &
    fi
    sleep $daemon_check_period
  done
  status $1
}

function status() {
  echo "current running status..."
  is=$(isRunning $1)
  if [ ${is} ]; then
    echo "$filename is running."
  else
    echo "$filename is not running."
  fi
}

function start() {
  is=$(isRunning $1)
  if [ ${is} ]; then
    #status $1
    echo "nothing happened."
    echo "$filename has been running."
  else
    status $1
    run $1
    status $1
  fi
}

function stop() {
  is=$(isRunning $1)
  while [ ${is} ]; do
    status $1
    pid=$(getPid $1)
    if [ "$pid" ]; then
      for p in ${pid[@]}
      do
          kill -15 $p >/dev/null 2>&1
      done
    else
      break
    fi
    sleep $check_period
    is=$(isRunning $1)
  done
  status $1
}

function restart() {
  operate="stop"
  stop $1
  operate="start"
  start $1
  operate="restart"
}

function restartlog() {
  operate="stop"
  stop $1
  operate="start"
  start $1
  operate="restartlog"
  tail -100f ${log_file_path}
}

function help() {
  echo "cp '<jarfile>' to '<jarfile>.sh'"
  echo "<jarfile>.jar.sh once"
  echo "<jarfile>.jar.sh start"
  echo "<jarfile>.jar.sh daemon"
  echo "<jarfile>.jar.sh restart"
  echo "<jarfile>.jar.sh restartlog"
  echo "<jarfile>.jar.sh stop"
  echo "<jarfile>.jar.sh status"

  echo "<self>.sh <jarfile> once"
  echo "<self>.sh <jarfile> start"
  echo "<self>.sh <jarfile> daemon"
  echo "<self>.sh <jarfile> restart"
  echo "<self>.sh <jarfile> restartlog"
  echo "<self>.sh <jarfile> stop"
  echo "<self>.sh <jarfile> status"
}












check_period=2
daemon_check_period=10
log_remain_line=100000

ip_addr=$(getIpAddr)


dir_absolute_path=$(cd $(dirname $0); pwd)
_curfilename=${0##*/}
if [[ ${_curfilename} =~ . ]]; then
    shfilename=${_curfilename}
    ext=$(getExt $_curfilename)
else
    echo "sorry! filename is error."
    exit 0
fi



if [[ ${_curfilename} =~ \..+\.sh ]]; then
  mode="dynamic"
else
  mode="static"
fi

if [ $mode == "dynamic" ]; then
  filename=${_curfilename%.*}
  file_path=$dir_absolute_path/$filename
  sh_file_path=$dir_absolute_path/$shfilename
  dir_path=${dir_absolute_path}
else
  file_path=($(readlink -f $2))
  filename=($(getFilename $file_path))
  sh_file_path=$dir_absolute_path/$shfilename
  dir_path=$(getDirectory ${file_path})
fi
log_path=${dir_path}/log
log_file_path=/dev/null
log_file_path=$log_path/service.log

args="-Xms512m -Xmx768m -XX:MetaspaceSize=128m -XX:+UseParNewGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -Xloggc:$log_path/gc.log ${argsInject}"


operate=$1
if [ -z ${operate} ]; then
  help
else
  mkdirLog
  if [ $operate == "once" ]; then
    start $file_path
  elif [ $operate == "start" ]; then
    start $file_path
  elif [ $operate == "restartlog" ]; then
    restartlog $file_path
  elif [ $operate == "daemon" ]; then
    if [ $mode == "dynamic" ]; then
      nohup $sh_file_path calldaemon >/dev/null 2>&1 &
    elif [ $mode == "static" ]; then
      nohup $sh_file_path calldaemon $file_path >/dev/null 2>&1 &
    fi
    sleep $check_period
    status $file_path
  elif [ $operate == "calldaemon" ]; then
    calldaemon $file_path
  elif [ $operate == "stop" ]; then
    stop $file_path
  elif [ $operate == "restart" ]; then
    restart $file_path
  elif [ $operate == "status" ]; then
    status $file_path
  elif [ $operate == "help" ]; then
    help
  else
    help
  fi
fi

