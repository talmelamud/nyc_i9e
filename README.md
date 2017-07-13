# nyc_i9e

Need to add jars to the **jars** folder in ie9 - before(!) starting the demo mode

http://central.maven.org/maven2/org/apache/hadoop/hadoop-aws/2.7.1/hadoop-aws-2.7.1.jar

http://central.maven.org/maven2/com/amazonaws/aws-java-sdk/1.7.4/aws-java-sdk-1.7.4.jar


Topology - 

master - 1  - in docker
slave -n - in docker
client - 1 - in host 

To make it work - 
1) On the client (host) add the jars - hadoop-aws-2.7.1.jar, aws-java-sdk-1.7.4.jar) - to the jars folder
2) Add to common-insightedge.sh in the client (host) the following lines (copy it to line 71, after the 'rm -rf ${artifact}' in the 'install_insightedge()' function) 

wget http://central.maven.org/maven2/org/apache/hadoop/hadoop-aws/2.7.1/hadoop-aws-2.7.1.jar -P /insightedge/jars
wget http://central.maven.org/maven2/com/amazonaws/aws-java-sdk/1.7.4/aws-java-sdk-1.7.4.jar -P /insightedge/jars
echo "spark.eventLog.enabled=true" > /insightedge/conf/spark-defaults.conf

3) run the cluster 