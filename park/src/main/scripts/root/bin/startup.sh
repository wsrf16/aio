#!/bin/bash

dir_absolute_path=$(cd $(dirname $0); pwd)
jar_file_path=${dir_absolute_path%/*}/${project.build.finalName}.jar
jarsh_file_path=${dir_absolute_path%/*}/${project.build.finalName}.jar.sh
sh_file_path=$dir_absolute_path/launch.sh

check_period=2
daemon_check_period=10
operate=$1

if [ -z ${operate} ]; then
  help
else
  if [ $operate == "once" ]; then
    # $sh_file_path once $jar_file_path
    $jarsh_file_path once
  elif [ $operate == "start" ]; then
    # $sh_file_path start $jar_file_path
    $jarsh_file_path start
  elif [ $operate == "daemon" ]; then
    # nohup $sh_file_path calldaemon $jar_file_path >/dev/null 2>&1 &
    # sleep $check_period
    # $sh_file_path status $jar_file_path
    nohup $jarsh_file_path calldaemon >/dev/null 2>&1 &
    sleep $check_period
    $jarsh_file_path status
  elif [ $operate == "calldaemon" ]; then
    # $sh_file_path calldaemon $jar_file_path
    $jarsh_file_path calldaemon
  elif [ $operate == "stop" ]; then
    # $sh_file_path stop $jar_file_path
    $jarsh_file_path stop
  elif [ $operate == "restart" ]; then
    # $sh_file_path restart $jar_file_path
    $jarsh_file_path restart
  elif [ $operate == "restartlog" ]; then
    # $sh_file_path restartlog $jar_file_path
    $jarsh_file_path restartlog
  elif [ $operate == "status" ]; then
    # $sh_file_path status $jar_file_path
    $jarsh_file_path status
  elif [ $operate == "help" ]; then
    # $sh_file_path help
    $jarsh_file_path help
  else
    $jarsh_file_path help
  fi
fi

