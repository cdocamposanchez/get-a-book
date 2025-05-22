import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useCart } from '../features/book/hooks/useCart';
import React, { useState, useRef, useEffect } from 'react';
import { FaHeart, FaSearch, FaUser } from 'react-icons/fa';
import { FaCartShopping } from "react-icons/fa6";
import { useSearch } from '../context/SearchContext';

const Navbar = () => {
    const { itemsCount } = useCart();
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const { searchTerm, setSearchTerm } = useSearch();
    const dropdownRef = useRef<HTMLDivElement>(null);

    const location = useLocation();
    const navigate = useNavigate();


    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(e.target.value);
    };

    const handleSearchSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        navigate('/home');
    };


    const handleLogoClick = () => {
        if (location.pathname === "/home") {
            window.location.reload();
        } else {
            navigate("/home");
        }
    };

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setDropdownOpen(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);


    return (
        <div className="bg-[#BF8924] flex items-center justify-between px-6 py-3 shadow relative">
            {/* Logo */}
            <Link
                to="/home"
                onClick={handleLogoClick}
                className="flex items-center cursor-pointer"
                aria-label="Ir a inicio"
            >
                <img
                    src="/assets/logo-full.png"
                    alt="Get-A-Book Logo"
                    className="h-10"
                />
            </Link>

            {/* Search */}
            <form onSubmit={handleSearchSubmit}
                  className={`relative flex-1 mx-6 max-w-xl ${location.pathname !== "/home" ? "hidden" : ""}`}>
                <input
                    type="text"
                    placeholder="¿Qué estás buscando hoy?"
                    className="w-full px-4 py-2 pr-10 rounded-l bg-white text-gray-700 text-base border-none outline-none"
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <button
                    type="submit"
                    className="absolute right-2 top-1/2 -translate-y-1/2 text-gray-600"
                    aria-label="Buscar"
                >
                    <FaSearch/>
                </button>
            </form>

            {/* Icons */}
            <div className="flex gap-5 text-xl items-center pr-10">
                <Link to="/favorites" className="hover:scale-110 transition-transform">
                    <FaHeart className="text-black"/>
                </Link>

                <div ref={dropdownRef} className="relative bg-transparent">
                    <button
                        onClick={() => setDropdownOpen(!dropdownOpen)}
                        className="hover:scale-110 transition-transform bg-transparent relative"
                    >
                        <FaUser className="text-black text-xl" />
                    </button>
                    {dropdownOpen && (
                        <div className="absolute left-1/2 -translate-x-1/2 top-full mt-2 bg-[#B3D4C6] text-black rounded-md shadow border border-gray-300 z-50">
                            <Link to="/profile" className="block px-4 py-2 hover:bg-[#a0cabc] transition-colors">
                                <p className="text-gray-700 text-center text-base">Ajustes</p>
                            </Link>
                            <Link to="/orders" className="block px-4 py-2 hover:bg-[#a0cabc] transition-colors">
                                <p className="text-gray-700 text-center text-base">Ordenes</p>
                            </Link>
                            <Link to="/returns" className="block px-4 py-2 hover:bg-[#a0cabc] transition-colors">
                                <p className="text-gray-700 text-center text-base">Devoluciones</p>
                            </Link>
                            <Link to="/logout" className="block px-4 py-2 hover:bg-[#a0cabc] transition-colors">
                                <p className="text-gray-700 text-center text-base">Cerrar Sesión</p>
                            </Link>
                        </div>
                    )}
                </div>

                <Link to="/cart" className="relative hover:scale-110 transition-transform">
                    <FaCartShopping className="text-black" />
                    {itemsCount > 0 && (
                        <span className="absolute -top-2 -right-3 bg-red-600 text-white text-xs font-bold px-1.5 py-0.5 rounded-full animate-pulse">
                            {itemsCount}
                        </span>
                    )}
                </Link>
            </div>
        </div>
    );
};

export default Navbar;
