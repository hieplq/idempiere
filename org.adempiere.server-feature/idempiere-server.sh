#!/bin/sh
#
unset DISPLAY
BASE=$(dirname "$( readlink -f "$0" )")
export ID_ENV=Server
. "$BASE"/utils/myEnvironment.sh
if [ "$JAVA_HOME" ]; then
  JAVA=$JAVA_HOME/bin/java
else
  JAVA=java
  echo JAVA_HOME is not set.
  echo You may not be able to start the server
  echo Set JAVA_HOME to the directory of your local JDK.
fi

if [ "$1" = "debug" ]; then
  DEBUG="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=4554,server=y,suspend=n"
fi

echo ===================================
echo Starting iDempiere Server
echo ===================================

# if don't set from service get default value
TELNET_PORT=${TELNET_PORT:-12612}
HOST=${HOST:-localhost}

VMOPTS="-Djetty.home=$BASE/jettyhome
-Djetty.base=$BASE/jettyhome
-Dosgi.console=$HOST:$TELNET_PORT
-Dlaunch.keep=true 
-Dlaunch.storage.dir=$BASE/bundle-cache 
"

"$JAVA" ${DEBUG} $IDEMPIERE_JAVA_OPTIONS $VMOPTS -jar "$BASE"/idempiere.server*.jar

