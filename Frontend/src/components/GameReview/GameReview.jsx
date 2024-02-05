import { fetchUser } from "../../hooks/FetchUserHook";
import GameRating from "../GameRating/GameRating";
import UserProfilePreview from "../UserProfilePreview";
import styles from "./GameReview.css"

export default function GameReview({gameReview}){
    const { user, isLoading, httpError } = fetchUser(null, gameReview.userID);
    return (
        <div className = "game-review-container">
            <div id = "reviewer-data">
                <UserProfilePreview user={user}/>
            </div>
            <div className= "game-review">
                <div id ="header"><p id = "review-tittle">{gameReview.reviewTitle}</p><div id = "rating"><GameRating gameScore={gameReview.reviewScore}/></div></div>
                {gameReview.reviewText !== null ? <p id = "review-text">{gameReview.reviewText}</p> : <p id ="default-text">No Description Provided.</p>}
            </div>
        </div>
    );
}