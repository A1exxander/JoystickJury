import ClickableGameArea from "./ClickableGameArea/ClickableGameArea.jsx";
import IndexSelectorList from "./IndexSelector/IndexSelectorList.jsx";
import styles from "./FeaturedGamesCenter.css"

export default function FeaturedGamesCenter({indexCount, currentlySelectedIndex, setIndex, game}){
    return (
        <div id ="featured-games-center">
            <ClickableGameArea game = {game} />
            <IndexSelectorList indexCount = {indexCount} currentlySelectedIndex={currentlySelectedIndex} setIndex={setIndex}/>
        </div>
    );
}