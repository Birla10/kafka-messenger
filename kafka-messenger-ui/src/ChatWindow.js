import React from 'react';

function ChatWindow({ messages }) { 
  return (
    <div className="chat-window">
      {messages.map((message, index) => (
        <p key={index}>{message.content}</p>
      ))}
    </div>
  );
}

export default ChatWindow;
