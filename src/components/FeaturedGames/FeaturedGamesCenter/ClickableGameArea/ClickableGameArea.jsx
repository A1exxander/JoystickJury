import { Link } from "react-router-dom"
import styles from './ClickableGameArea.css';

export default function ClickOnGame({game}){
    return(<Link id ="clickable-area" to={`/games/${game.gameID}`}><div><p></p></div></Link>);
}