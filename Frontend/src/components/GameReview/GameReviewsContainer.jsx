import { fetchGamesReviewsHook } from "../../hooks/fetchGameReviewsHook";
import GameReviewList from "./GameReviewsList";
import React, { useState } from "react"; // Import useState from 'react'
import styles from "./GameReviewsContainer.css"

export default function GameReviewsContainer({gameID}) {
    
    const { gameReviews, httpError, isLoading } = fetchGamesReviewsHook(gameID, 3);
    const [jwt, setJwt] = useState(localStorage.getItem('JWT'));
    const [signedInStatus, setSignedInStatus] = useState(jwt !== null);

    if (isLoading) {
        return <p id="message">Loading...</p>
    }
    else if (httpError !== null) {
        return <p id="message">Error! {httpError}</p>
    }
    else { 
        return (
            <div id="game-reviews-container">
                <GameReviewList gameReviews={gameReviews}/>
                <button className="button-61" role="button" id="post-review-button">Post Review</button>
            </div>
        );
    }
}
