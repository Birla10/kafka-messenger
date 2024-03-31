import React, { useEffect, useRef } from 'react';

function ChatWindow({ messages }) {
  // Create a ref for the chat container
  const chatEndRef = useRef(null);

  // Scroll to the bottom of the chat window whenever messages update
  useEffect(() => {
    if (chatEndRef.current) {
      chatEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]); // Depend on messages

  return (
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
  );
}

export default ChatWindow;
