import styles from "./IndexSelectorListItem.css";

export default function IndexSelectorListItem({index, currentlySelectedIndex, setIndex}){
    return (
        (index === currentlySelectedIndex) ? <li className = "index-selector-list-item-active" onClick={() => setIndex(index)}></li> : <li className = "index-selector-list-item" onClick={() => setIndex(index)}></li>
    );
}