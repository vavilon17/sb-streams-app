curl -i -X POST \
         -H "Accept:application/json" \
         -H "Content-Type:application/json" \
         127.0.0.1:8083/connectors/ \
         --data "@debezium.json"