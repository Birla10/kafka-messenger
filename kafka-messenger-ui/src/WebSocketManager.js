// WebSocketManager.js
import React, { useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

const WebSocketManager = ({ onMessageReceived }) => {
  const stompClient = useRef(null);
  const isSubscribed = useRef(false);

  useEffect(() => {
    const connectWebSocket = () => {
      if (stompClient.current?.connected && isSubscribed.current) {
        // Already connected and subscribed
        return;
      }      
      const socket = new SockJS('http://localhost:9090/ws');
      stompClient.current = Stomp.over(socket);
      stompClient.current.debug = () => {};

      stompClient.current.connect({}, () => {
        console.log('WebSocket Connected');
        if (!isSubscribed.current) {
          const receiver = localStorage.getItem('user');
          console.log("in websocket", receiver);          
          stompClient.current.subscribe(`/user/${receiver}/queue/messages`, (message) => {
            console.log("Message received from websocket");
            onMessageReceived(message.body.trim());
          });
          isSubscribed.current = true;
        }
      }, (error) => {
        console.error('Connection error: ', error);
        isSubscribed.current = false;
        // Reconnect with a delay
        setTimeout(connectWebSocket, 5000);
      });
    };

    connectWebSocket();

    // Return cleanup function
    return () => {
      // No need to disconnect, as we want to keep the connection alive
    };
  }, [onMessageReceived]);

  return null; // This component doesn't render anything itself
};

export default WebSocketManager;
