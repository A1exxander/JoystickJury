import { fetchGames } from "../../hooks/fetchGamesHook.jsx";
import ActiveTabGameCard from "./ActiveTabGameCard.jsx";
import styles from './ActiveTabGames.css';
import { Link } from "react-router-dom";

export default function ActiveTabGames({activeTab, limit}){

    const { games, httpError, isLoading } = fetchGames(activeTab, limit);

    if (isLoading) {
        return <p id = "message">Loading...</p>
    }
    else if (httpError !== null) {
        return <p id = "message">Error! {httpError}</p>
    }
    else if (games.length === 0){
        return <p id = "message">No games found!</p>
    }
    else{
        return (
            <div id = "active-tab-games">
                {games.map((game) => (
                        <ActiveTabGameCard game={game} />
                ))}
            </div>
        );
    }


}