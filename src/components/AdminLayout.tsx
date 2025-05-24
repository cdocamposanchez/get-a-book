import {Outlet, NavLink, Link, useNavigate, useLocation} from 'react-router-dom';
import {FaBook, FaPlusSquare } from 'react-icons/fa';
import { useEffect, useRef, useState } from 'react';
import { FaUser } from 'react-icons/fa';

const AdminNavbar = () => {
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null);

    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setDropdownOpen(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    const handleLogoClick = () => {
        if (location.pathname === "/admin") {
            window.location.reload();
        } else {
            navigate("/admin");
        }
    };

    return (
        <div className="bg-[#BF8922] flex items-center justify-between px-6 py-3 shadow relative">
            <Link
                to="/admin"
                onClick={handleLogoClick}
                className="flex items-center cursor-pointer"
                aria-label="Ir a inicio"
            >
                <img
                    src="/assets/logo-admin.png"
                    alt="Get-A-Book Logo"
                    className="h-10"
                />
            </Link>
            <div ref={dropdownRef} className="relative mr-5">
                <button onClick={() => setDropdownOpen(!dropdownOpen)} className="hover:scale-110 transition-transform">
                    <FaUser className="text-black text-xl" />
                </button>
                {dropdownOpen && (
                    <div className="absolute left-1/2 -translate-x-1/2 top-full mt-2 bg-white rounded shadow border z-50 p-1">
                        <NavLink to="/home" className="block p-2 hover:bg-gray-100 rounded">
                            Home
                        </NavLink>
                        <NavLink to="/logout" className="block p-2 hover:bg-gray-100 rounded">
                           Logout
                        </NavLink>
                    </div>
                )}
            </div>
        </div>
    );
};

const Sidebar = () => {
    return (
        <div className="w-64 bg-[#f4f4f4] h-screen shadow-lg p-4 space-y-4">
            <NavLink
                to="/admin/books"
                className={({ isActive }) =>
                    `flex items-center gap-3 p-2 rounded hover:bg-[#e0e0e0] hover:scale-110 transition-transform ${isActive ? 'bg-[#d1d1d1] font-semibold' : ''}`
                }
            >
                <FaBook /> Libros
            </NavLink>
            <NavLink
                to="/admin/create-book"
                className={({ isActive }) =>
                    `flex items-center gap-3 p-2 rounded hover:bg-[#e0e0e0] hover:scale-110 transition-transform ${isActive ? 'bg-[#d1d1d1] font-semibold' : ''}`
                }
            >
                <FaPlusSquare /> Crear Libro
            </NavLink>
        </div>
    );
};

const AdminLayout = () => {
    return (
        <div className="flex flex-col min-h-screen">
            <AdminNavbar />
            <div className="flex">
                <Sidebar />
                <main className="max-h-[90] p-2 bg-[#80AFAB] flex-1">
                    <Outlet />
                </main>
            </div>
        </div>
    );
};


export default AdminLayout;
