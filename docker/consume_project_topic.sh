docker exec -it $(docker ps | grep docker_kafka | awk '{print $1}') /kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic SMARTBRICK.public.project