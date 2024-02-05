import styles from './TabsList.css';

export default function TabsList({tabs, activeTab, setActiveTab}) {
    return (
        <div id="tabs-list">
            {tabs.map((tab, index) => {
                if (tab === activeTab) {
                    return <p id="active-tab" key={tab}>{tab}</p>;
                } else {
                    return <p key={tab} onClick={() => {setActiveTab(tabs[index])}}>{tab}</p>;
                }
            })}
        </div>
    );
}