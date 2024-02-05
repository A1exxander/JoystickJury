import { fetchGame } from "../../hooks/FetchGameHook";
import GameRating from "../GameRating/GameRating";
import GameReviewList from "../GameReview/GameReviewsList";
import { Link } from "react-router-dom";
import styles from "./GameData.css"
import GameReviewsContainer from "../GameReview/GameReviewsContainer";

export default function GameData({gameID}){

    const {game, httpError, isLoading} = fetchGame(gameID);

    if (isLoading){
        return <p>Loading loading loading...</p>
    }
    else if (httpError){
        return <p>Error! {httpError}</p>
    }
    else {
        return (
            <div id="game-data-container" className="game-grid">
              <h2 id="game-name">{game.gameTitle}</h2>
              <p id = "developer-name">{game.developerName}</p>
              <img id="game-cover-art" src={game.gameCoverArtLink} alt="game cover art" />
              <p id = "release-date">{game.releaseDate}</p>
              <a id="watch-trailer-a"href={game.gameTrailerLink} target="_blank" >
                <button className="button-61" role="button" id="watch-trailer-button">Watch Trailer</button>
              </a>
              <p id="game-description">{game.gameDescription}<br></br> <br></br> {game.gameGenres.join(', ')} <br></br></p>
              { game.releaseStatus === "RELEASED" ? (
              <> <GameRating gameScore={game.averageRating} /> <GameReviewsContainer gameID={gameID} /></>) : 
              null}</div>
          );          
    }
}