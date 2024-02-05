import { useState } from 'react'
import Header from "../components/Header/Header.jsx";
import FeaturedGames from '../components/FeaturedGames/FeaturedGames.jsx';
import DisplayedGames from '../components/DisplayedGames/DisplayedGames.jsx';
import GameSearchBar from '../components/GameSearchBar/GameSearchBar.jsx';
import Footer from '../components/Footer/Footer.jsx';

export default function HomePage() {

  return (
    <body>
    <Header />
    <main>
        <FeaturedGames />
        <GameSearchBar id ="game-search-bar-container" placeholderText={"Enter a search query... "} />
        <DisplayedGames />
    </main>
    <Footer/>
    </body>
  )
}
