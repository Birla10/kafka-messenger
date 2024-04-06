// sendMessage.js
import axios from 'axios';

const API_URL = 'http://localhost:9090/api/produce';

export const sendMessage = async (message, receiver) => {
  try {
    const sender = localStorage.getItem('user');
    await axios.post(API_URL, {
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ sender, message, receiver}),
      
    });
  } catch (error) {
    console.error("Error sending message:", error);
  }
};