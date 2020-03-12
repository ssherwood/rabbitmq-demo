# RabbitMQ :: Spring AMQP Demos

## Overview

## Getting Started

### Starting RabbitMQ

In the `./docker` folder is a docker-compose configuration for a single node RabbitMQ cluster.  To start
the cluster, just run `docker-compose up` (note this compose file has a persistent volume defined, so if
you completely want to blow away the configuration, use `--force-recreate` or just manually delete the volume).

If you have a licensed version of IntelliJ, you can run it internally to the IDE (it has a nice
"Services" tab that lets you see the logs for the service).

## Additional Resources
* https://www.rabbitmq.com/tutorials/amqp-concepts.html
* https://www.compose.com/articles/configuring-rabbitmq-exchanges-queues-and-bindings-part-1/
* https://www.compose.com/articles/configuring-rabbitmq-exchanges-queues-and-bindings-part-2/