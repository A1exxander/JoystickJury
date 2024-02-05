import { useState } from "react";
import './GameSearchBar.css'

export default function GameSearchBar({placeholderText}){ // Ideally to make a super reusable search bar, make a generic searchbar component that executes a callback

  const [searchText, setSearchText] = useState(''); // Initialize search text state

  const handleInputChange = (event) => {
    setSearchText(event.target.value);
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      // Redirect to the /search page when Enter is pressed
      const trimmedSearchText = searchText.trim();
      if (trimmedSearchText) {
        window.location.href = `/search?query=${trimmedSearchText}`;
      }
    }
  };

  return (
    <div id = "search-bar-container">
      <input
        id="game-search-bar"
        type="text"
        placeholder={placeholderText}
        value={searchText}
        onChange={handleInputChange}
        onKeyPress={handleKeyPress}
      />
    </div>
  );
  
};
