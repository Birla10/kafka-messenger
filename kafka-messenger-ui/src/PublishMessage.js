// sendMessage.js
import axios from 'axios';

const API_URL = 'http://localhost:9090/api/produce';

export const sendMessage = async (message) => {
  try {
    await axios.post(API_URL, message, {
      headers: { 'Content-Type': 'text/plain' }
    });
  } catch (error) {
    console.error("Error sending message:", error);
  }
};
