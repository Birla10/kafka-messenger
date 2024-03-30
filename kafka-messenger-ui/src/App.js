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
    setMessages((prevMessages) => [...prevMessages, message]);
  };

  const handleLogin = (username) => {
    localStorage.setItem("user", username);
    setUser(username);
    navigate('/chatwindow');
  };

  const sendMessage = (message) => {
    // This function can be used to send messages if needed
    setMessages((prevMessages) => [...prevMessages, message]);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Kafka Messenger</h1>
      </header>
      <Routes>
        <Route
          path="/"
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
