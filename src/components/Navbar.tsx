import { Link } from 'react-router-dom';
import { useCart } from '../features/book/hooks/useCart';

const Navbar = () => {
    const { itemsCount } = useCart();

    return (
        <div className="bg-[#f2c94c] flex items-center justify-between px-6 py-3 shadow">
            <div className="flex items-center">
                <Link to="/home">
                    <img src="/logo.png" alt="Get-A-Book Logo" className="h-10 cursor-pointer" />
                </Link>
            </div>
            <input
                type="text"
                placeholder="¿Qué estás buscando?"
                className="flex-1 mx-6 max-w-xl px-4 py-2 rounded-full bg-black text-white text-base border-none outline-none"
            />
            <div className="flex gap-5 text-black text-xl relative">
                <Link to="/favorites" className="hover:scale-110 transition-transform">❤️</Link>

                <Link to="/cart" className="relative hover:scale-110 transition-transform">
                    🛒
                    {itemsCount > 0 && (
                        <span className="absolute -top-2 -right-3 bg-red-600 text-white text-xs font-bold px-1.5 py-0.5 rounded-full animate-pulse">
                            {itemsCount}
                        </span>
                    )}
                </Link>

                <Link to="/returns" className="hover:scale-110 transition-transform">🔁</Link>
                <Link to="/orders" className="hover:scale-110 transition-transform">📦</Link>
                <Link to="/profile" className="hover:scale-110 transition-transform">👤</Link>
            </div>
        </div>
    );
};

export default Navbar;
