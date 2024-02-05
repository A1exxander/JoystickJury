import Header from "../components/Header/Header.jsx";
import Footer from '../components/Footer/Footer.jsx';
import styles from './AboutPage.css';

export default function AboutPage(){
    return (
        <body>
            <Header />
            <main id = "main">
                <h2>Joystick Jury</h2>
                    <div id = "jj-description">
                        <p>
                            Welcome to <strong>Joystick Jury</strong>, your ultimate destination for unbiased and in-depth video game reviews. Our platform is dedicated to providing gamers with the latest insights and analyses of the newest releases in the gaming world.
                        </p>
                        <p>
                            At Joystick Jury, we pride ourselves on our passionate team of experienced gamers and reviewers who bring their extensive knowledge to our audience. We believe in helping gamers make informed decisions about their next gaming adventure.
                        </p>
                        <p>
                            Whether you're a casual player or a hardcore enthusiast, Joystick Jury offers something for everyone. Join our community and dive into the world of gaming with reviews you can trust.
                        </p>
                        <p>Happy gaming!</p>
                    </div>
                    <button id = "return-button" className = "button-61" onClick = {() => {window.history.back()}}>Explore</button>
            </main>
            <Footer />
            </body>
    );
}