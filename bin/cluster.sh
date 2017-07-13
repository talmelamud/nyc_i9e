#!/usr/bin/env bash
set -x

export MASTER=172.17.0.2
export SLAVES=172.17.0.3,172.17.0.4,172.17.0.5
export IEPATH=/insightedge

./sbin/insightedge.sh --mode undeploy \
  --master $MASTER

./sbin/insightedge.sh  --install --mode remote-master --hosts $MASTER \
  --user root --key ~/.ssh/id_rsa \
  --path $IEPATH --master $MASTER --group insightedge

./sbin/insightedge.sh  --install --mode remote-slave --hosts $SLAVES \
  --user root --key ~/.ssh/id_rsa \
  --path $IEPATH --master $MASTER  --group insightedge

./sbin/insightedge.sh --mode deploy \
  --master $MASTER  --topology 3,1 --group insightedge
