
import { Link, useLocation } from "react-router-dom"
import {useState, useEffect } from "react"
import styles from "./Header.css";
import ConnectButton from "../Connect/ConnectButton";
import CurrentUserProfilePreview from "../UserProfile/CurrentUserProfilePreview";

export default function Header(){

    const currentWebpage = useLocation().pathname;
    const [jwt, setJwt] = useState(localStorage.getItem('JWT'));
    const [signedInStatus, setSignedInStatus] = useState(jwt !== null);

    return (
        
    <header>
        <svg id = "jj-logo" width="100px" height="auto" viewBox="0 0 128 128" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" aria-hidden="true" role="img" class="iconify iconify--noto" preserveAspectRatio="xMidYMid meet" fill="#000000"> <g id="SVGRepo_bgCarrier" stroke-width="0"/> <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"/> <g id="SVGRepo_iconCarrier"> <path d="M63.83 52.62c-5.04.32-13.84 5.04-26.08 10.2s-23.29 9.87-25.11 10.73c-1.82.86-3.33 1.37-3.43 4.38s38.63 27.6 38.63 27.6l38.1.64l32.05-28.74s-.05-2.11-2.11-3.78C113.94 72.08 97 65.5 90.34 62.82c-6.65-2.69-24.23-10.34-26.51-10.2z" fill="#858585"/> <path d="M67.34 100.9c6.87-.1 15.25-5.55 28.11-12.32c9.91-5.22 19.9-10.68 20.33-10.85c1.62-.64 1.46-2.57 1.46-2.57s1.46 2.13 1.65 6.44c.17 3.87.11 12.56 0 13.63c-.11 1.07.21 2.79-2.25 4.19s-38.61 21.29-40.78 22.43c-2.26 1.19-4.61 2.6-7.42 2.6c-2.53.01-5.41-1.24-9.63-2.66c-2.02-.68-13.53-5.53-24.77-10.98c-7.51-3.64-15.62-7.63-20.31-10.31c-2.69-1.54-3.55-2.66-3.87-3.44c-.98-2.37-.71-16.77-.72-18.06c-.02-1.9.42-3 .42-3s1.26.99 5.03 2.79c3.32 1.58 13.73 7.1 22.75 11.09c22.1 9.76 26.79 11.07 30 11.02z" fill="#5e6367"/> <path d="M63.88 57.54c-7.07.03-11.3 2.41-13.92 5.41c-3.45 3.96-3.42 9.03-3.15 11.23c.46 3.85 5.2 9 17.97 9c13.9 0 16.64-6.06 17.09-7.86c.47-1.89.83-8.66-3.77-13.13c-2.59-2.51-7.72-4.68-14.22-4.65z" fill="#282828"/> <path d="M57.81 42.79v20.39s1.02 3.08 6.18 3.11c5.29.03 6.27-2.47 6.27-2.47l.02-21.18l-12.47.15z" fill="#9618af"/> <path d="M63.96 3.56c-13.39-.08-21.62 9.16-21.7 20.93c-.08 11.77 9.77 21.28 21.62 21.16c13.75-.15 21.61-9.05 21.48-22.29c-.11-12.63-10.32-19.74-21.4-19.8z" fill="#ff4f28"/> <path d="M59.66 8.07c-2.49-1.33-6.76-.18-10.55 4.56c-3.78 4.71-4.35 12.07-.18 12.67c3.69.53 4.11-3.49 4.27-4.62c.36-2.53 2.27-5.51 4.62-6.31c.78-.26 2.69-.59 3.25-1.87c.85-1.97.09-3.63-1.41-4.43z" fill="#fdfffc"/> <path d="M23.89 76.26c.14 3.81 5.62 5.42 10.08 5.42c6.19 0 9.76-2.51 9.69-5.83c-.08-3.68-5.44-5.46-9.9-5.52c-5.17-.05-10.01 2.04-9.87 5.93z" fill="#dd0c22"/> <path d="M27.39 74.51c-.07 2.26 3.04 3.18 6.29 3.18s5.94-1.13 6.01-3.32c.07-2.19-3.89-3.18-6.44-3.04c-2.54.14-5.79.78-5.86 3.18z" fill="#ff4c2a"/> </g> </svg>
        <nav>
            {currentWebpage === "/" ? <Link to = "/"><p className ="navbar-active-link">Home</p></Link> : <Link to = "/"><p className ="navbar-link">Home</p></Link>}
            {currentWebpage === "/about" ? <Link to = "/about"><p className ="navbar-active-link">About</p></Link> : <Link to = "/about"><p className ="navbar-link">About</p></Link>}
            {currentWebpage === "/categories" ? <Link to = "/categories"><p className ="navbar-active-link">Categories</p></Link> : <Link to = "/categories"> <p className ="navbar-link">Categories</p></Link>}
        </nav>
        <div>
            { (signedInStatus === false) ? <ConnectButton setSignedInStatus={ () => {setSignedInStatus(true)}}/> : <CurrentUserProfilePreview userJWT={jwt} updateJWTState = {setJwt}/>}
        </div>
    </header>

    ); // Update to use global state checking if user isnt null - If it isnt, render their profile picture, else, render a sign in button. 
}