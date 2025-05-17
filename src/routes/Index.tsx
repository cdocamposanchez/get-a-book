import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Orders from "../pages/Orders";
import Tracking from "../pages/Tracking";
import Cart from "../pages/Cart";
import Favorites from "../pages/Favorites";
import Returns from "../pages/Returns";

function AppRoutes() {
  return (
    <Router>
      <Routes>
        <Route path="/orders" element={<Orders />} />
        <Route path="/tracking" element={<Tracking />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/favorites" element={<Favorites />} />
        <Route path="/returns" element={<Returns />} />
        <Route path="" element={<Orders />} /> {/ Ruta por defecto */}
      </Routes>
    </Router>
  );
}

export default AppRoutes;