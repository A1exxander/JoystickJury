import styles from "./ContainerEdgeNavButton.css"

export default function ContainerEdgeNavButton({icon, onClickHandler}){
    return (
        <div id = "container-edge-nav-button">
            <button id = "svg-button" onClick={onClickHandler}>{icon}</button>
        </div>
      );
}