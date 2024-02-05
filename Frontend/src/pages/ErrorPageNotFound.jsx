import styles from './ErrorPage.css';
import Header from '../components/Header/Header';
import Footer from '../components/Footer/Footer';
export default function ErrorPageNotFound(){

    return (
        <body>
        <Header/>
        <main>
            <img src = "https://media2.giphy.com/media/8L0Pky6C83SzkzU55a/giphy.gif" alt = "error-image"></img>
            <p id = "error-message">The requested webpage was not found</p>
            <button id = "return-button" className = "button-61" onClick = {() => {window.history.back()}}>Return Back</button>
        </main>
        </body>
    );

}

