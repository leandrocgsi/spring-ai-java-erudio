import { useState } from 'react';

import './App.css';

function App() {

  const [activeTab, setActiveTab] = useState('ask-ai')

  const handleTabChange = (tab) => {
    alert(tab);
    setActiveTab(tab);
  }

  return (
    <div className="App">
      <button onClick={() => handleTabChange('ask-ai')}>Talk with AI</button>
      <button onClick={() => handleTabChange('recipe-generator')}>Generate Recipes</button>
      <button onClick={() => handleTabChange('image-generator')}>Generate Images</button>
    </div>
  );
}

export default App;
