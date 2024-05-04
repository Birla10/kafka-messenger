# kafka-messenger

This is a Chat Application developed to get Hands-on experience with Java REST APIs, Kafka, Websocket, Spring Security, ReactJS

Prerequisites:
  1.ReactJS
  2.NodeJS
  3.Java 17
  4.Kafka(If you are using windows Download WSL2 for Windows, Download and install Kafka from the official website)

This project has 2 main folders
1. Kafka-messenger-ui
2. kafka-messenger-backend
   
Steps to run this project:
If you have insatlled kafka in WSL then
  1. Start Kafka zookeeper (bin/zookeeper-server-start.sh config/zookeeper.properties)
  2. Start kafka Server (bin/kafka-server-start.sh config/server.properties)
or if you have Kafka in Local Machine then use
  1. C:\kafka_2.13-3.6.0\bin\zookeeper-server-start.bat C:\kafka_2.13-3.6.0\config\zookeeper.properties
  2. C:\kafka_2.13-3.6.0\bin\kafka-server-start.bat C:\kafka_2.13-3.6.0\config\server.properties
  
  3. Start the Backend server(I used Spring tool Suite, as it is very handy to run Spring Maven projects) in any IDE
  4. Then run npm install by opening an integrated terminal in kafka-messenger-ui folder to download all the dependencies.
  5. Start the UI Server using npm start
  6. This will open the application in a localhost.

If you are facing issues while running the project then check for the free ip address for the kafka server by running "ip addr show eth0". 
Then replace the spring.kafka.bootstrap-servers property with the one you got by running above command

