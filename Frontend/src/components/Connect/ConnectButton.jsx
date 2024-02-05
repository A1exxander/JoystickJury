import { useState } from "react";
import ConnectOverlay from "./ConnectOverlay";

export default function ConnectButton({setSignedInStatus}) {

  const [connectButtonToggled, setConnectButtonToggled] = useState(false);

  return (
    <>
    <button className = "button-61" onClick={ ()=> {setConnectButtonToggled(!connectButtonToggled);}}>
      Connect
    </button>
    {(connectButtonToggled) ? <ConnectOverlay setSignedInStatus = {setSignedInStatus} toggleConnectOverlay = { ()=> {setConnectButtonToggled(!connectButtonToggled);} }/> : null}
    </>
  );
}
