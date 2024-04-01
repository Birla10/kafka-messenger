import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ChatList({ onUserSelect }) {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    setUsers([]);
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get('http://localhost:9090/api/fetchusers');
      console.log(response.data);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  return (
    <div className="chat-list">
      <ul>
        {users.map((user) => (
          <a href='/chatwindow'><li key={user} onClick={() => onUserSelect(user)}>
            {user}
          </li></a>
        ))}
      </ul>
    </div>
  );
}

export default ChatList;
