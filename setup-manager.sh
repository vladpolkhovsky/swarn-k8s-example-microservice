curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

git clone https://github.com/vladpolkhovsky/swarn-k8s-example-microservice.git

cd swarn-k8s-example-microservice

docker build --tag vpolkhovsky/swarm-k8s-example-microservice .
docker push vpolkhovsky/swarm-k8s-example-microservice

#Инициализация кластера
docker swarm init --advertise-addr 185.159.128.138

#Узнать токен для подключения воркера
docker swarm join-token worker

docker stack deploy -c docker-compose-swarm.yml swarm-stack

echo "my-secret-username-for-database" | docker secret create db_username -
echo "my-secret-password-for-database" | docker secret create db_password -

#ДОБАВИТЬ к app и закоментировать переменные
    secrets:
      - source: db_username
        target: spring.datasource.username
      - source: db_password
        target: spring.datasource.password

apt update
apt install -y snapd
snap install k8s --classic
k8s bootstrap
k8s status --wait-ready
