import { useState, useEffect } from 'react';

export function fetchGame(gameID) {  // Renamed for clarity

    const [game, setGame] = useState(null);
    const [httpError, setHttpError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {

        let endpoint = `http://localhost:8080/api/v1/games/${gameID}`;

        fetch(endpoint, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) { 
                throw new Error(`Error! Invalid HTTP Request. Return status: ${response.status}`);
            }
            return response.json(); 
        })
        .then(data => {
            setGame(data, ()=>{console.log(game)});
        })
        .catch(error => {
            setHttpError(error.message);
        })
        .finally(() => {
            setIsLoading(false);
        });
        
    }, []);

    return { game, httpError, isLoading };
    
}