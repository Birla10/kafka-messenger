// App.js
import React, { useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import ChatWindow from "./ChatWindow";
import MessageInput from "./MessageInput";
import WebSocketManager from "./WebSocketManager";
import Login from "./Login";
import ChatList from './ChatList';

import "./App.css";

function App() {
  const [user, setUser] = useState(localStorage.getItem("user") || "");
  const [messages, setMessages] = useState([]);
  const [activeChat, setActiveChat] = useState(null); // State to keep track of the active chat
  const navigate = useNavigate();

  // This function could be updated to handle a new message in the context of a chat with a specific user
  const handleMessageReceived = (message) => {
    console.log("WebSocket message received:", message);
    setMessages((prevMessages) => [
      ...prevMessages,
      { text: message, source: "websocket" },
    ]);
  };

  const handleLogin = (token, username) => {
    localStorage.setItem("token", token);
    localStorage.setItem("user", username); // Update this to use 'user' to be consistent
    setUser(username);
    navigate("/chatwindow");
  };

  const sendMessage = (message) => {
    console.log("Updating messages state in App with:", message);
    setMessages((prevMessages) => [
      ...prevMessages,
      { text: message, source: "input" },
    ]);
  };
  
  // Function to handle selecting a user from the ChatList
  const handleUserSelect = (selectedUser) => {
    setActiveChat(selectedUser);
    // Here you might want to load the chat history for the selected user
  };

  return (
    <div className="App">
      <header className="App-header">
        <h3>Kafka Messenger</h3>
      </header>
      <Routes>
        <Route path="/" element={<Login onLogin={handleLogin} />} />
        {user && (
          
          <Route
            path="/chatwindow"
            element={
              <div className="chat-container">
                <div className="chat-list">
                  <ChatList onUserSelect={handleUserSelect} />{" "}
                  {/* Pass users when you have them */}
                </div>
                <WebSocketManager onMessageReceived={handleMessageReceived} />
                <div className="chat-interface">
                  <div className="chat-window-container">
                    <ChatWindow messages={messages} />
                    <MessageInput onSendMessage={sendMessage} />
                  </div>
                </div>
              </div>
            }
          />
        )}
      </Routes>
    </div>
  );
}

export default App;
