#!/usr/bin/env bash
# exit on errors
set -o errexit -o errtrace -o nounset -o functrace -o pipefail
shopt -s inherit_errexit 2>/dev/null || true
trap 'sk-catch --exit_code $? --line $LINENO --linecallfunc "$BASH_COMMAND" --funcstack $(printf "::%s" ${FUNCNAME[@]}) -o stdout ' ERR

# import shellkit functions
source shellkit_bootstrap.sh

# defaults
current_dir=`pwd`
checkout_name=$(basename `pwd`)
NAME="$(basename "${0}")"
build_envs="prod sandbox qa int"
tag="v2.0.1"
current_dir=`pwd`

#
# functions
#


usage(){
I_USAGE="

  Usage: ${NAME} [OPTIONS]

  Description:
    setup environment for running lint tools

  General usage:

"
  echo "$I_USAGE"
  exit

}

#
# args
#

while :
do
  case ${1-default} in
      --*help|-h         )  usage ; exit 0 ;;
      -v | --verbose )       verbose_arg='-v' VERBOSE=$((VERBOSE+1)); shift ;;
      --) shift ; break ;;
      -*) echo "WARN: Unknown option (ignored): $1" >&2 ; shift ;;
      *)  break ;;
    esac
done

#
# setup build environment from .tool-versions
#

sk-asdf-install-tool-versions
# set JAVA_HOME
. ~/.asdf/plugins/java/set-java-home.bash
_asdf_java_update_java_home

sk-dir-make ~/log

