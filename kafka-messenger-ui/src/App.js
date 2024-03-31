import React, { useState } from "react";
import ChatWindow from "./ChatWindow";
import MessageInput from "./MessageInput";
import WebSocketManager from "./WebSocketManager";
import Login from "./Login";
import "./App.css";
import { Route, Routes, useNavigate } from "react-router-dom";

function App() {
  const [user, setUser] = useState(localStorage.getItem("user") || "");
  const [messages, setMessages] = useState([]);
  const navigate = useNavigate();

  const handleMessageReceived = (message) => {
    console.log('WebSocket message received:', message);
    setMessages((prevMessages) => [...prevMessages, { text: message, source: 'websocket' }]);
  };

  const handleLogin = (username) => {
    localStorage.setItem("user", username);
    setUser(username);
    navigate('/chatwindow');
  };

  const sendMessage = (message) => {
    // You should send the message to the server here as well
    console.log('Updating messages state in App with:', message);
    setMessages((prevMessages) => [...prevMessages, { text: message, source: 'input' }]);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Kafka Messenger</h1>
      </header>
      <Routes>
        <Route
          path="/login"
          element={
            <>
              <Login onLogin={handleLogin} />
            </>
          }
        ></Route>
        {user && (
          <Route
            path="/chatwindow"
            element={
              <>
                <WebSocketManager onMessageReceived={handleMessageReceived} />
                <ChatWindow messages={messages} />
                <MessageInput onSendMessage={sendMessage} />
              </>
            }
          />
        ) }
      </Routes>
    </div>
  );
}

export default App;
