import React, { useState } from 'react';
import ChatWindow from './ChatWindow';
import MessageInput from './MessageInput';
import './App.css';

function App() {
  const [messages, setMessages] = useState([]);

  const sendMessage = (message) => {
    setMessages([...messages, message]);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Kafka Messenger</h1>
      </header>
      <ChatWindow messages={messages} />
      <MessageInput onSendMessage={sendMessage} />
    </div>
  );
}

export default App;
