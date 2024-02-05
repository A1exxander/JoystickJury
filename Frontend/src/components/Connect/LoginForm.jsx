import { useState, useEffect } from "react";
import styles from "./Form.css";
import { TextField, Button } from "@mui/material";


export default function LoginForm({ switchToRegisterForm, setSignedInStatus }) {

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  // Handle form submission
  const handleSubmit = (e) => {

    e.preventDefault();
    
    fetch("http://localhost:8080/api/v1/users/token", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)})
      .then((response) => {
        if (response.status != 200) {
          throw new Error(response);
        } 
        return response.text();
      })
      .then((data) => {
        localStorage.setItem("JWT", data);
        alert("Welcome! Successfully logged in!");
        setSignedInStatus(true);
      })
      .catch((error) => {alert("Invalid login attempt. Credentials did not match any on server ")});
    
  };

  return (
    <div id={"form-container"}>
      <h2>Login</h2>

      <form id="form" onSubmit={handleSubmit}>
      <div className="form-item">
          <TextField
            label="Email"
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            placeholder="Example@example.com"
            required
            fullWidth
            size="small" 
            color="error"
          />
        </div>
        <div className="form-item">
          <TextField
            label="Password"
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            placeholder="********"
            required
            fullWidth
            size="small" 
            color="error"
          />
        </div>
        <button type="submit" id ="submit-button" className = "button-61">Login</button>
      </form>

      <p id = "switch-form-text">New? Click{" "}<span id={"switch-form-link"} onClick={switchToRegisterForm}>here</span>{" "}to register</p>
    </div>
  );
}
