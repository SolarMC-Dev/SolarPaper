#!/bin/bash
#
# update-submodules
#
# Updates all submodules of a git project.
# by Greg Krsak <greg.krsak@gmail.com>, Feb. 4, 2014
#

read -r -d '' USAGE << EOF
Usage: update-submodules [<options>]
Options:
-b BRANCH    use the provided branch
-r           update sub-submodules
-h           show this message, then exit\n
EOF

OPTIONS='b:rh'
BRANCH='master'

while getopts $OPTIONS option
do
  case $option in
    b) BRANCH=$OPTARG
       ;;
    r) RECURSIVE=true
       ;;
    h) printf "$USAGE" && exit 2
       ;;
    *) printf "$USAGE" && exit 1
       ;;
  esac
done

shift $(($OPTIND - 1))

git submodule foreach git pull origin $BRANCH
if [ "$RECURSIVE" == true ]; then
  git submodule foreach git submodule foreach git pull origin $BRANCH
fi

# End of update-submodules
