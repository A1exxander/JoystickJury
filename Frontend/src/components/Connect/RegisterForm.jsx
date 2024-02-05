import React, { useState } from "react";
import { TextField, Button } from "@mui/material";
import styles from "./Form.css";

export default function RegisterForm({ switchToLoginForm, setSignedInStatus }) {

  const [formData, setFormData] = useState({
    email: "",
    displayName: "",
    password: "",
    confirmPassword: "",
    profilePictureLink: ""
  });

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {

    e.preventDefault();

    const requestBody = {
      userCredentialsDTO: {
        email: formData.email,
        password: formData.password,
      },
      userDTO: {
        userID: null,
        displayName: formData.displayName,
        profilePictureLink: formData.profilePictureLink,
        profileDescription: null,
        accountType: "REVIEWER",
      }

    };
  
    fetch("http://localhost:8080/api/v1/users", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestBody)})
    .then((response) => {
      if (response.status !== 200) {
        throw new Error(response);
      } 
      return response.text();
    })
    .then((data) => {
      localStorage.setItem("JWT", data);
      alert("Welcome! Successfully registered user!");
      window.location.reload();
      
    })
    .catch((error) => alert(error.message))
    
  };

  return (

    <div id={"form-container"}>
      <h2 id ="form-header">Register</h2>

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
            label="Display Name"
            type="text"
            id="displayName"
            name="displayName"
            value={formData.displayName}
            onChange={handleInputChange}
            placeholder="John Doe"
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
        <div className="form-item">
          <TextField
            label="Confirm Password"
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleInputChange}
            placeholder="********"
            required
            fullWidth
            size="small" 
            color="error"
          />
        </div>
        <div className ="form-item">
        <TextField
            label="Profile Picture Link"
            type="text"
            id="profilePictureLink"
            name="profilePictureLink"
            value={formData.profilePictureLink}
            onChange={handleInputChange}
            placeholder="Example.com/avatar.jpeg"
            fullWidth
            size="small" 
            color="error"
          />
        </div>
        <button
          type="submit"
          id="submit-button"
          className="button-61" // Hide the Material-UI button
        >
          Register
        </button>
      </form>

      <p id = "switch-form-text">Already have an account? Click{" "}<span id={"switch-form-link"} onClick={switchToLoginForm}>here</span>{" "}to login</p>

    </div>
  );
}
