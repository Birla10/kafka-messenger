import axios from 'axios';

const API_URL = 'http://localhost:9090/api/messages/produce'; 

export const sendMessage = async (message) => {
  try {
    const response = await axios.post(API_URL, message, {
      headers: { 'Content-Type': 'text/plain' }
    });
    return response.data;
  } catch (error) {
    console.error("Error sending message:", error);
    throw error;
  }
};
