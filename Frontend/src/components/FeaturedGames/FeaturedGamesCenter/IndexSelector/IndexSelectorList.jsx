import styles from "./IndexSelectorList.css";
import IndexSelectorListItem from "./IndexSelectorListItem";

export default function IndexSelectorList({indexCount, currentlySelectedIndex, setIndex}){

    const indexItems = [];
    for (let i = 0; i < indexCount; i++) {
    indexItems.push(
      <IndexSelectorListItem key = {i} index = {i} currentlySelectedIndex = {currentlySelectedIndex} setIndex = {setIndex}/>
    );
  }

    return (       
        <ul id = "index-selector-list">
            {indexItems}
        </ul>
    );
}