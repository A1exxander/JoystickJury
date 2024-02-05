import React from 'react';
import styles from './GameReviewsList.css';
import UserProfilePreview from '../UserProfilePreview';
import GameReview from './GameReview';

export default function GameReviewList({ gameReviews }) {
  // Check if gameReviews is undefined or an empty array
  if (!gameReviews || gameReviews.length === 0) {
    return (
        <div id = "game-review-list-container">
            <p id="message">No reviews found. Become the first to post a review!</p>
        </div>
    );
  }

  return (
    <div id = "game-review-list-container">
      {gameReviews.map((currentGameReview) => (
        <GameReview gameReview={currentGameReview}/>
      ))}
    </div>
  );
}
