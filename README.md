# javaPractice

本工程为 学习java过程中的一些知识点实践和工作过程中的所用技能的延伸。巩固知识，方便开发时copy代码
项目结构如下：

- mybatis: mybatis 和 mybatis-plus 相关
- concurrent: 并发相关（多线程）
- saas: 多租户根据header schema动态切换数据源
- kafka kafka1: kafka相关

    单节点环境 docker-compose.yml 文件
    ``version: '2'
      
      services:
        zoo1:
          image: wurstmeister/zookeeper
          restart: unless-stopped
          hostname: zoo1
          ports:
            - "2181:2181"
          container_name: zookeeper
      
        # kafka version: 1.1.0
        # scala version: 2.12
        kafka1:
          image: wurstmeister/kafka
          ports:
            - "9092:9092"
          environment:
            KAFKA_ADVERTISED_HOST_NAME: localhost
            KAFKA_ZOOKEEPER_CONNECT: "zoo1:2181"
            KAFKA_BROKER_ID: 1
            KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
            KAFKA_CREATE_TOPICS: "stream-in:1:1,stream-out:1:1"
          depends_on:
            - zoo1
          container_name: kafka



