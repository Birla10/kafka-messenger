import React, { useState } from 'react';
import axios from 'axios';

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async () => {
    setError(''); // Clear any existing errors
    try {
      // Replace with your API endpoint
      const response = await axios.post('http://localhost:9090/login', {
        username: username.trim(),
        password: password.trim(),
      });
      onLogin(response.data); // Assuming the response contains the data needed for login
    } catch (err) {
      // If there's an error, set it to be displayed.
      setError(err.response?.data?.message || 'Login failed');
    }
  };

  // Handle form submission to prevent default browser behaviour
  const handleSubmit = (e) => {
    e.preventDefault();
    handleLogin();
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {error && <div className="error">{error}</div>}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
