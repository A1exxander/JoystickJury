import LoginForm from "./LoginForm";
import { useState } from "react";
import { useEffect } from 'react';
import RegisterForm from "./RegisterForm";
import styles from "./ConnectOverlay.css";

export default function ConnectOverlay({ setSignedInStatus, toggleConnectOverlay}) {

    const FormTypes = {
        SIGNUP: "sign-up",
        LOGIN: "log-in"
    };
    
    const [formType, setFormType] = useState(FormTypes.LOGIN);

    useEffect(() => {
        document.body.style.overflow = 'hidden';
        return () => document.body.style.overflow = 'unset';
    }, []);

    
    return (
        <div id = "connect-overlay" onClick = {toggleConnectOverlay}>
            <div id ="connect-window" onClick={(event) => { event.stopPropagation();}}>
                {(formType === FormTypes.LOGIN) ? <LoginForm setSignedInStatus = {setSignedInStatus} switchToRegisterForm={() => {setFormType(FormTypes.SIGNUP);}}/> :
                <RegisterForm setSignedInStatus = {setSignedInStatus} switchToLoginForm={() => {setFormType(FormTypes.LOGIN);}} /> }
             </div>
        </div>
    );

}