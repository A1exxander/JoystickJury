import { useState, useEffect } from "react";
import TabsList from "./TabsList";
import styles from './DisplayedGames.css';
import ActiveTabGames from "./ActiveTabGames";

export default function DisplayedGames() {
  const [tabs, setTabs] = useState(["Latest", "Highest-Rated", "Upcoming"]);
  const [activeTab, setActiveTab] = useState(tabs[1]);
  const [displayedGamesLimit] = useState(4);
  
  // Check if the user is logged in and add the "recommended" tab if true
  useEffect(() => {
    const userIsLoggedIn = localStorage.getItem("JWT") !== null;
    if (userIsLoggedIn) {
      setTabs((prevTabs) => [...prevTabs, "Recommended"]);
    }
  }, []);

  return (
    <div id="displayed-games-container">
      <TabsList tabs={tabs} activeTab={activeTab} setActiveTab={setActiveTab}/>
      <ActiveTabGames activeTab={activeTab} limit ={displayedGamesLimit}/>
    </div>
  );

}
