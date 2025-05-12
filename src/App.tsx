import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import reactLogo from './assets/react.svg';
import viteLogo from "../../../../../../../../../../../vite.svg";
import './App.css';

import ProfilePage from './pages/ProfilePage';
import LoginPage from './pages/LoginPage';

function App() {
    const [count, setCount] = useState(0);

    const isAuthenticated = localStorage.getItem('authToken');

    // @ts-ignore
    // @ts-ignore
    // @ts-ignore
    // @ts-ignore
    return (
        <Router>
            <div>
                <a href="https://vite.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="logo react" alt="React logo" />
                </a>
            </div>
            <h1>Vite + React</h1>

            {}
            <Routes>
                {}
                <Route path="/login" element={<LoginPage />} />

                {}
                <Route
                    path="/profile"
                    element={isAuthenticated ? <ProfilePage></ProfilePage> : <Navigate to="/login" />}
                />

                {}
            </Routes>

            <div className="card">
                <button onClick={() => setCount((count) => count + 1)}>
                    count is {count}
                </button>
                <p>
                    Edit <code>src/App.tsx</code> and save to test HMR
                </p>
            </div>
            <p className="read-the-docs">
                Click on the Vite and React logos to learn more
            </p>
        </Router>
    );
}

export default App;

