import { useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

const WebSocketManager = ({ onMessageReceived }) => {
  useEffect(() => {
    let stompClient = null;

    const connectWebSocket = () => {
      // Establish the WebSocket connection using SockJS and Stomp
      const socket = new SockJS('http://localhost:9090/ws');
      stompClient = Stomp.over(socket);

      stompClient.connect({}, () => {
        console.log('WebSocket Connected');
        stompClient.subscribe('/topic/messages', (message) => {
            const messageJson = {
            destination: message.headers.destination,
            contentType: message.headers['content-type'],
            subscription: message.headers.subscription,
            messageId: message.headers['message-id'],
            contentLength: parseInt(message.headers['content-length'], 10),
            content: message.body.trim()
          };
          // Call the callback function when a new message is received
          onMessageReceived(messageJson);
        });
      }, (error) => {
        console.error('Connection error: ', error);
      });
    };

    connectWebSocket();

    return () => {
      if (stompClient && stompClient.connected) {
        stompClient.disconnect(() => {
          console.log('WebSocket Disconnected');
        }, {});
      }
    };
  }, [onMessageReceived]);

  return null; // This component doesn't render anything itself
};

export default WebSocketManager;
