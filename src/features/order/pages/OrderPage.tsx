import React from 'react';
import '../styles/OrderPage.css';

const OrderPage = () => {
  const orders = [
    {
      id: '123456',
      creationDate: '2025-05-10',
      departureDate: '2025-05-11',
      deliveryEstimate: '2025-05-13',
      status: 'En camino',
    },
    {
      id: '654321',
      creationDate: '2025-04-20',
      departureDate: '2025-04-21',
      deliveryEstimate: '2025-04-25',
      status: 'Entregado',
    },
    {
      id: '987654',
      creationDate: '2025-05-01',
      departureDate: '2025-05-02',
      deliveryEstimate: '2025-05-04',
      status: 'Pendiente',
    },
  ];

  const groupedOrders = {
    'En camino': orders.filter((o) => o.status === 'En camino'),
    'Pendiente': orders.filter((o) => o.status === 'Pendiente'),
    'Entregado': orders.filter((o) => o.status === 'Entregado'),
  };

  return (
    <div className="orders-container">
      <div className="order-page">
        <h2>Mis Órdenes</h2>
        <div className="order-columns">
          {Object.entries(groupedOrders).map(([status, orders]) => (
            <div className="order-column" key={status}>
              <h3>{status}</h3>
              {orders.map((order) => (
                <div className="order-card" key={order.id}>
                  <div className="order-info">
                    <p><strong>ORDEN:</strong> #{order.id}</p>
                    <p><strong>ESTADO:</strong> {order.status}</p>
                    <p><strong>FECHA DE CREACIÓN:</strong> {order.creationDate}</p>
                    <p><strong>FECHA DE SALIDA:</strong> {order.departureDate}</p>
                    <p><strong>ENTREGA ESTIMADA:</strong> {order.deliveryEstimate}</p>
                  </div>
                  <button className="details-button">DETALLES</button>
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default OrderPage;
