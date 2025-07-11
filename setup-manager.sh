apt install ufw
apt install git

ufw default deny incoming
ufw default allow outgoing
ufw allow ssh
ufw allow 5000/tcp

ufw allow 2377/tcp
ufw allow 7946/tcp
ufw allow 7946/udp
ufw allow 4789/udp

ufw allow 8080/tcp
ufw allow 8080/udp

ufw enable
ufw reload

curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

#создать /etc/docker/daemon.json
#{
#  "insecure-registries": ["185.159.128.138:5000"]
#}
sudo systemctl restart docker

docker run -d -p 5000:5000 --restart always --name registry --volume registry-volume registry:latest

git clone https://github.com/vladpolkhovsky/swarn-k8s-example-microservice.git

cd swarn-k8s-example-microservice

docker build --tag swarm-k8s-example-microservice .
docker tag swarm-k8s-example-microservice 185.159.128.138:5000/swarm-k8s-example-microservice
docker push 185.159.128.138:5000/swarm-k8s-example-microservice

#Инициализация кластера
docker swarm init --advertise-addr 185.159.128.138

#Узнать токен для подключения воркера
docker swarm join-token worker

docker stack deploy -c docker-compose-swarm.yml swarm-stack

echo "my-secret-username-for-database" | docker secret create spring.datasource.username -
echo "my-secret-password-for-database" | docker secret create spring.datasource.password -
