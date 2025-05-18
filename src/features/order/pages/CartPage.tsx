import React from 'react';
import '../styles/CartPage.css';

const CartPage = () => {
  const items = [
    {
      id: 1,
      title: 'Cien años de soledad',
      author: 'Gabriel García Márquez',
      price: 54000,
      image: 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?fit=crop&w=100&q=80',
    },
    {
      id: 2,
      title: 'Rayuela',
      author: 'Julio Cortázar',
      price: 48000,
      image: 'https://images.unsplash.com/photo-1544717305-996b815c338c?fit=crop&w=100&q=80',
    },
  ];

  const total = items.reduce((acc, item) => acc + item.price, 0);

  return (
    <div className="cart-container">
      <h1>Carrito de compras</h1>
      <div className="cart-items">
        {items.map(item => (
          <div className="cart-item" key={item.id}>
            <img src={item.image} alt={item.title} />
            <div className="item-details">
              <h2>{item.title}</h2>
              <p>{item.author}</p>
              <p className="price">${item.price.toLocaleString()}</p>
            </div>
            <button className="remove-button">Eliminar</button>
          </div>
        ))}
      </div>
      <div className="cart-summary">
        <p>Total: <strong>${total.toLocaleString()}</strong></p>
        <button className="checkout-button">Proceder al pago</button>
      </div>
    </div>
  );
};

export default CartPage;
