#! /bin/sh

# Older way: java -XX:+AggressiveHeap -jar $1 -c2p $2

# Exemple : ./pnml2cami.sh path_to_models_folder

JAR_PATH=fr.lip6.pnml.cami2pnml-2.1.13.jar

NBPARAM=1
E_NOFILE=66
E_ERROR=1
E_SUCCESS=0

# Update the following configuration of the JVM as you see fit, or keep these defaults.
JVM_ARGS="-d64 -server -Xmx7g -Xmn128m -XX:NewSize=2g -XX:MaxNewSize=2g -XX:+UseNUMA -XX:+UseConcMarkSweepGC -XX:+UseParNewGC"

if [ $# -ne "$NBPARAM" ] 
	then 
	 echo "Usage: `basename $0` path_to_models_folder"
	 exit "$E_NOFILE" 
fi

# To convert from CAMI to PNML, just replace the option -p2c by -c2p

for file in $1/*.pnml
do
	java $JVM_ARGS -jar $JAR_PATH -p2c $file &> $file.pnml2cami.log || exit "$E_ERROR"
done

exit "$E_SUCCESS"



