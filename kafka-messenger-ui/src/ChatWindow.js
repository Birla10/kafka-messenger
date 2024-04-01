import React, { useEffect, useRef, useState } from 'react';
import ChatList from './ChatList';

function ChatWindow({ messages }) {
  // Create a ref for the chat container
  const chatEndRef = useRef(null);
  const [activeChat, setActiveChat] = useState(null); // State to keep track of the active chat

  // Scroll to the bottom of the chat window whenever messages update
  useEffect(() => {
    if (chatEndRef.current) {
      chatEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]); // Depend on messages

    // Function to handle selecting a user from the ChatList
  const handleUserSelect = (username) => {
      setActiveChat(username);
      // Here you might want to load the chat history for the selected user
    };

  return (
    <div className="chat-container">
    <div className="chat-list">
    <ChatList onUserSelect={handleUserSelect} /> {/* Pass users when you have them */}
    </div>
    <div className="chat-window">
      {messages.map((message, index) => (
        // Render your message here
        <p key={index} className={`message ${message.source}`}>
          {message.text}
        </p>
      ))}
      {/* This div is used as a marker for scrolling to the bottom */}
      <div ref={chatEndRef} />
    </div>
    </div>
  );
}

export default ChatWindow;
