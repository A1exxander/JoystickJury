import styles from "./GameRating.css"

export default function GameRating({gameScore}){
    if (gameScore === null){
        return (<p id = "review-tbd-text">TBD</p>);
    }
    else if(gameScore > 5 || gameScore < 1){
        return (<p>Error! Invalid gamescore provided.</p>);
    }
    else {
        let remainingStars = gameScore;
        const gameScoreStars = [];
        for (let i = 0; i < 5; i++){

            if (remainingStars >= 1){ // Render a full star
                gameScoreStars.push( 
                    <svg viewBox="0 0 576 512" width="100" title="star" key={i}>
                    <path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" fill="gold"/>
                    </svg>
                );
                --remainingStars;
            }
            else if (remainingStars < 1 && remainingStars > 0){ // Render a half star
                gameScoreStars.push( 
                    <svg viewBox="0 0 576 512" width="100" title="star" key={i}>
                    <path
                    d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"
                    fill="gold"
                    />
                    <path
                        d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"
                        fill="lightgrey" // Set the fill color to transparent for the top half
                    />
                    </svg>
                );
                remainingStars = 0;
            }
            else { // Render an empty star
                gameScoreStars.push(
                    <svg viewBox="0 0 576 512" width="100" title="star" key={i}>
                    <path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" fill = "lightgrey" />
                    </svg>
                );
            }
            
        }

        return <div id="rating-stars-container">{gameScoreStars}</div>;

    }
}