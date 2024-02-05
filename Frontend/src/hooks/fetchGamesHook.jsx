import { useState, useEffect } from 'react';

export function fetchGames(extension = "", limit = null) {
  const [games, setGames] = useState(null);
  const [httpError, setHttpError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  let jwt; // Declare jwt variable outside of the condition

  useEffect(() => {
    let endpoint = `http://localhost:8080/api/v1/games/${extension.toLowerCase()}`;

    if (limit !== null) {
      endpoint += `?limit=${limit}`;
    }

    // Check if extension is "Recommended" to send the JWT
    if (extension === "Recommended") {
      jwt = localStorage.getItem("JWT"); // Assign jwt inside the condition
      fetch(endpoint, {
        method: "GET",
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `${jwt}`,
        },
      })
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.message);
          }
          return response.json();
        })
        .then((data) => {
          setGames(data);
        })
        .catch((error) => {
          setHttpError(error.message);
        })
        .finally(() => {
          setIsLoading(false);
        });
    } else {
      // If it's not "Recommended" or there's no JWT, make the request without JWT
      fetch(endpoint, {
        method: "GET",
        headers: {
          'Content-Type': 'application/json',
        }
      })
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.message);
          }
          return response.json();
        })
        .then((data) => {
          setGames(data);
        })
        .catch((error) => {
          setHttpError(error.message);
        })
        .finally(() => {
          setIsLoading(false);
        });
    }
  }, [extension, jwt]);

  return { games, httpError, isLoading };
  
}
