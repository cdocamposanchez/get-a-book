import { useCart } from "../../book/hooks/useCart";
import type { CartItem } from "../../book/hooks/useCart";


export default function CartPage() {
  const {
    cartItems,
    removeFromCart,
    updateQuantity,
    total,
    clearCart,
  } = useCart();

  return (
    <div className="cart-page">
      <h1>Tu carrito</h1>
      {cartItems.length === 0 ? (
        <p>El carrito está vacío.</p>
      ) : (
        <div>
          {cartItems.map((item: CartItem) => (
            <div key={item.id} className="cart-item">
              <img src={item.image} alt={item.title} width={80} />
              <div>
                <h2>{item.title}</h2>
                <p>${item.price} x {item.quantity}</p>
                <div>
                  <button onClick={() => updateQuantity(item.id, item.quantity - 1)}>-</button>
                  <span>{item.quantity}</span>
                  <button onClick={() => updateQuantity(item.id, item.quantity + 1)}>+</button>
                  <button onClick={() => removeFromCart(item.id)}>Eliminar</button>
                </div>
              </div>
            </div>
          ))}
          <h3>Total: ${total.toFixed(2)}</h3>
          <button onClick={clearCart}>Vaciar carrito</button>
        </div>
      )}
    </div>
  );
}

