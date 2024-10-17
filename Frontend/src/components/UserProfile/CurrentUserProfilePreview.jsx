import { Link, useLocation } from "react-router-dom"
import {useState, useEffect } from "react"
import styles from "./CurrentUserProfilePreview.css";
import { fetchUser } from "../../hooks/FetchUserHook";

export default function CurrentUserProfilePreview( {userJWT, updateJWTState} ) {

    const { user, isLoading, httpError } = fetchUser(userJWT);

    if (isLoading){
        return <p>Loading...</p>
    }
    else if (httpError){
        return <p>Error! {httpError}</p>
        alert(httpError);
    }
    else {
        return (
        <div id = "current-profile-preview">
            <img src ={user.profilePictureLink}></img>
            <div id = "dropdown-menu">
                <Link to={`/user/${user.userID}`}>Profile</Link>
                <button onClick={() => {
                    localStorage.removeItem("JWT");
                    updateJWTState(null);
                    location.reload();
                }}>Log-out</button>
            </div>
        </div>
        );
    }

}
