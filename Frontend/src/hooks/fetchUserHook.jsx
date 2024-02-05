import { useState, useEffect } from 'react';

export function fetchUser(userJWT, userID = null) {
  
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHTTPError] = useState(null);

  useEffect(() => {

    let endpoint = "http://localhost:8080/api/v1/users/";

    if (userID === null){
      endpoint += "current";
    }
    else {
      endpoint += userID;
    }

    fetch(endpoint, {
      method: "GET",
      headers: {
        'Content-Type': 'application/json',
        "Authorization": userJWT
      }
    })
    .then((response) => {
      if (response.status !== 200) {
        throw new Error(response.message);
      }
      return response.json();
    })
    .then((data) => {
      setUser(data);
    })
    .catch((error) => {
      setHTTPError(error.message);
      localStorage.removeItem("JWT");
    })
    .finally(() => {
      setIsLoading(false);
    });
  }, [userJWT]);

  return { user, isLoading, httpError };
}
