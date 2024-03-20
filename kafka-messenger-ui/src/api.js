import axios from 'axios';

const API_URL = 'http://localhost:3001/api/messages'; 

export const sendMessage = async (message) => {
  try {
    const response = await axios.post(API_URL, message);
    return response.data;
  } catch (error) {
    console.error("Error sending message:", error);
    throw error;
  }
};
