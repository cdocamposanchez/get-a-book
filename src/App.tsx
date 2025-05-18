import { BrowserRouter } from 'react-router-dom';

import './App.css';

import Navbar from './components/navbar/Navbar.tsx';
import AppRoutes from "./routes/AppRoutes.tsx";

function App() {

  return (
    <BrowserRouter>
        <Navbar />
        <AppRoutes />
    </BrowserRouter>
  );
}

export default App;