// src/MessageInput.js
import React, { useState } from 'react';
import { sendMessage as publishMessage } from './PublishMessage'; // Renamed for clarity

function MessageInput({ onSendMessage }) { // This prop is the local state updater from App
  const [input, setInput] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (input.trim()) {
      console.log('Sending message:', input);
      try {
        // First, try to send the message to the server/publish to Kafka
        await publishMessage(input);
        console.log('message published:', input);
        // If successful, then update the local state to show the message
        onSendMessage(input);
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
