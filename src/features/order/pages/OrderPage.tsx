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
      <div className="min-h-screen bg-[#6e9c9f] px-10 py-6 font-sans">
        <div className="max-w-7xl mx-auto">
          <h2 className="text-3xl font-bold text-black mb-8">Mis Órdenes</h2>
          <div className="flex flex-wrap justify-between gap-6">
            {Object.entries(groupedOrders).map(([status, orders]) => (
                <div className="flex-1 min-w-[300px] max-w-[48%]" key={status}>
                  <h3 className="text-xl font-semibold text-black mb-4">{status}</h3>
                  {orders.map((order) => (
                      <div
                          key={order.id}
                          className="bg-gray-100 rounded-xl p-4 mb-5 flex justify-between items-start shadow"
                      >
                        <div className="text-sm text-gray-800 space-y-1">
                          <p><strong>ORDEN:</strong> #{order.id}</p>
                          <p><strong>ESTADO:</strong> {order.status}</p>
                          <p><strong>FECHA DE CREACIÓN:</strong> {order.creationDate}</p>
                          <p><strong>FECHA DE SALIDA:</strong> {order.departureDate}</p>
                          <p><strong>ENTREGA ESTIMADA:</strong> {order.deliveryEstimate}</p>
                        </div>
                        <button className="ml-4 bg-gray-300 hover:bg-gray-400 text-sm font-bold text-white py-2 px-4 rounded-lg transition">
                          DETALLES
                        </button>
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
