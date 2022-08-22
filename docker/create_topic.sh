docker exec -it $(docker ps | grep docker_kafka | awk '{print $1}') /kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic $1 --config min.insync.replicas=1
