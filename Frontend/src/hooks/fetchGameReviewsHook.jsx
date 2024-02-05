import { useState, useEffect } from "react";

export function fetchGamesReviewsHook(gameID, limit){
    
    const [gameReviews, setGameReviews] = useState(null);
    const [httpError, setHttpError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        let endpoint = `http://localhost:8080/api/v1/games/${gameID.toLowerCase()}/reviews`;
        if (limit !== null || limit !== 0){
            endpoint += "?limit=" + limit;
        }
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
            setGameReviews(data)
        })
        .catch(error => {
            setHttpError(error.message);
        })
        .finally(() => {
            setIsLoading(false);
        });
        
    }, []);

    return { gameReviews, httpError, isLoading };

}
