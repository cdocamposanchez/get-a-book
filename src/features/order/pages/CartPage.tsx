import  { useEffect, useState } from "react";
import type { CartItem } from "../../../types/cartItem"
import CartItemCard from "../components/CartItemCard";
import type {Order} from "../../../types/order";
import CheckoutModal from "../components/CheckoutModal.tsx";
import orderService from "../OrderService.ts";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../user/hooks/useAuth.ts";

const CartPage = () => {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const navigate = useNavigate();
  const { token } = useAuth();

  useEffect(() => {
    const stored = localStorage.getItem("cart");
    if (stored) {
      setCartItems(JSON.parse(stored));
    }
  }, []);

    const handleProceedToCheckout = () => {
        if (!token) {
            navigate("/login?redirect=/cart");
            return;
        }
        setIsModalOpen(true);
    };

  const total = cartItems.reduce(
      (sum, item) => sum + item.price * item.quantity,
      0
  );

    const handleOrderSubmit = async (order: Order) => {
        try {
            const url = await orderService.createStripeSession(order);
            localStorage.setItem("pendingOrder", JSON.stringify(order));
            window.location.href = url;
        } catch (error) {
            console.error("Error al crear sesión de Stripe", error);
        }
    };

    return (
      <main className="max-h-[90vh] bg-[#80AFAB] p-8 overflow-y-auto">
        <h1 className="text-3xl font-bold text-center mb-8 text-black">Carrito de Compras</h1>
        <div className="max-w-4xl mx-auto">
          {cartItems.length === 0 ? (
              <p className="text-center text-gray-600 text-lg">
                Tu carrito está vacío.
              </p>
          ) : (
              <>
                {cartItems.map((item) => (
                    <CartItemCard
                        key={item.id}
                        item={item}
                        onUpdate={(updatedItem) => {
                          setCartItems((prev) =>
                              prev.map((i) => (i.id === updatedItem.id ? updatedItem : i))
                          );
                        }}
                        onRemove={(id) => {
                          setCartItems((prev) => prev.filter((i) => i.id !== id));
                        }}
                    />
                ))}

                <div className="mt-8 bg-white rounded-lg shadow p-6 text-right border">
                  <h2 className="text-xl font-bold text-gray-800">
                    Total: ${total.toFixed(2)}
                  </h2>
                  <button
                      className="mt-4 px-6 py-2 bg-green-600 text-black rounded hover:bg-green-700
                      font-semibold cursor-pointer hover:scale-105 transition-transform border border-black"
                          onClick={() => handleProceedToCheckout()}>
                    Proceder al Pago
                  </button>
                </div>
              </>
          )}
        </div>

          <CheckoutModal
              isOpen={isModalOpen}
              onClose={() => setIsModalOpen(false)}
              cartItems={cartItems}
              onSubmit={handleOrderSubmit}
          />
      </main>
  );
};

export default CartPage;
