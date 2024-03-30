// src/MessageInput.js
import React, { useState } from 'react';
import { sendMessage } from './PublishMessage'; // Import the service function

function MessageInput() {
  const [input, setInput] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (input.trim()) {
      try {
        console.log("inside handle submit",input)
        await sendMessage( input ); // Adapt the object structure based on your backend
        setInput(''); // Clear the input after sending the message
      } catch (error) {
        console.error("Failed to send message:", error);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        value={input}
        onChange={(e) => setInput(e.target.value)}
        type="text"
        placeholder="Type a message..."
      />
      <button type="submit">Send</button>
    </form>
  );
}

export default MessageInput;
