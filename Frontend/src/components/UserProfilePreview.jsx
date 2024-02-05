import { Link } from "react-router-dom"
import styles from "./UserProfilePreview.css";

export default function UserProfilePreview({user}){
    if (user === null ){
        return <p>Failed to load user profile!</p>
    }
    return (
        <Link to={`/user/${user.userID}`}>
            <div id = "user-profile-preview">
                <p id ="display-name">{user.displayName}</p>
                <img id = "profile-pic" src ={user.profilePictureLink}></img>
            </div>
        </Link>
    );
}