import styles from './ActiveTabGameCard.css';
import GameRating from '../GameRating/GameRating'
import { Link } from 'react-router-dom';

export default function ActiveTabGameCard({game}){
    if (game.releaseStatus === "RELEASED") {
        return (
            <div id = "game-card-container">
                <h4>{game.gameTitle}</h4>
                <Link to={`/games/${game.gameID}`} key={`/games/${game.gameID}`}>
                    <img id = "game-card-picture" src={game.gameCoverArtLink}></img>
                </Link>
                {(game.averageRating === null) ? <div><p id ="tbd-text">TBD</p></div> : <GameRating gameScore={game.averageRating}/>}
            </div>
        );
    }
    else {
        return (
            <div id = "game-card-container">
                <h4>{game.gameTitle}</h4>
                <Link to={`/games/${game.gameID}`} key={`/games/${game.gameID}`}>
                    <img id = "game-card-picture" src={game.gameCoverArtLink}></img>
                </Link>
            </div>
        );
    }
}