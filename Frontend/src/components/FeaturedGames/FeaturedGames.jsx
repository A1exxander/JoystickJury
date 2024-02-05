import styles from "./FeaturedGames.css";
import { useState, useEffect } from "react";
import ContainerEdgeNavButton from "./ContainerEdgeNavButton/ContainerEdgeNavButton.jsx";
import { LinearProgress } from "@mui/material";
import IndexSelectorList from "./FeaturedGamesCenter/IndexSelector/IndexSelectorList.jsx";
import { fetchGames } from "../../hooks/fetchGamesHook.jsx";
import { Link } from "react-router-dom";
import ClickableGameArea from "./FeaturedGamesCenter/ClickableGameArea/ClickableGameArea.jsx";
import FeaturedGamesCenter from "./FeaturedGamesCenter/FeaturedGamesCenter";

export default function FeaturedGames() {

  const {games, httpError, isLoading } = fetchGames("trending", 5);
  const [index, setIndex] = useState(0);
  const [progressValue, setProgressValue] = useState(0);

  const decrementIndex = () => {
    setIndex((prevIndex) => (prevIndex - 1 + games.length) % games.length);
  };

  const incrementIndex = () => {
    setIndex((prevIndex) => (prevIndex + 1) % games.length);
  };

  useEffect(() => { // Update our progress by 1 every 100 MS

    let intervalID = setInterval( () => setProgressValue(progressValue+1), 100);

    if (progressValue === 100){
      setProgressValue(0);
      (index === 4) ? setIndex(0) : setIndex(index + 1);
    }

    return () => {
      clearInterval(intervalID);
    };
  });

  useEffect(() => { // When our index changes, reset the state of our progress to 0 - Change the wallpaper as well of the background image 
    setProgressValue(0);
  }, [index]);

  if (isLoading){
    return (<p>Loading...</p>);
  }
  else if (httpError){
    return (<p id = "error-text">Error {httpError}</p>);
  }
  else {
  return (
    <div >
      <div id = "featured-games-container" style={{ backgroundImage: `url(${games[index].gameBannerArtLink})`}}>
        <ContainerEdgeNavButton icon={<svg style={{ transform: 'scale(2)' }} xmlns="http://www.w3.org/2000/svg" width="48" height="48"> <path d="M12 2a10 10 0 1 0 10 10A10.011 10.011 0 0 0 12 2zm0 18a8 8 0 1 1 8-8 8.009 8.009 0 0 1-8 8z"/> <path d="M13.293 7.293 8.586 12l4.707 4.707 1.414-1.414L11.414 12l3.293-3.293-1.414-1.414z"/></svg>}onClickHandler={decrementIndex}/>
        <FeaturedGamesCenter indexCount={5} currentlySelectedIndex={index} setIndex={setIndex} game ={games[index]}/>
        <ContainerEdgeNavButton icon={ <svg style={{ transform: 'scale(2)' }}xmlns="http://www.w3.org/2000/svg"width="48"height="48"><path d="M12 2a10 10 0 1 0 10 10A10.011 10.011 0 0 0 12 2zm0 18a8 8 0 1 1 8-8 8.009 8.009 0 0 1-8 8z"/><path d="M9.293 8.707 12.586 12l-3.293 3.293 1.414 1.414L15.414 12l-4.707-4.707-1.414 1.414z"/></svg>}onClickHandler={incrementIndex}/>
      </div> 
      <LinearProgress variant="determinate" value={progressValue} color="secondary"/>  
    </div>
  );
  }
}