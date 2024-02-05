import { Routes, Route, useParams } from 'react-router-dom';
import Header from "../components/Header/Header.jsx";
import Footer from '../components/Footer/Footer.jsx';
import GameData from '../components/GameData/GameData.jsx';

export default function HomePage() {

    const {gameID} = useParams();

        return (
            <main>
                <Header />
                    <body>
                        <GameData gameID={gameID}/>
                    </body>
                <Footer/>
            </main>
        );

}
