// ChatList.js
import React from 'react';

function ChatList({ users, onUserSelect }) {
  return (
    <div className="chat-list">
      <ul>
        {users.map((user) => (
          <li key={user} onClick={() => onUserSelect(user)}>
            {user}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ChatList;
