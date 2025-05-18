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
      <div className="p-8 font-sans">
        <h1 className="text-3xl font-bold text-center mb-8">Carrito de compras</h1>

        <div className="flex flex-col gap-6">
          {items.map(item => (
              <div
                  className="flex items-center bg-gray-100 p-4 rounded-lg shadow-sm"
                  key={item.id}
              >
                <img
                    src={item.image}
                    alt={item.title}
                    className="w-20 h-28 object-cover mr-4 rounded"
                />
                <div className="flex-grow">
                  <h2 className="text-lg font-semibold">{item.title}</h2>
                  <p className="text-gray-600">{item.author}</p>
                  <p className="text-teal-600 font-bold">${item.price.toLocaleString()}</p>
                </div>
                <button className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 transition">
                  Eliminar
                </button>
              </div>
          ))}
        </div>

        <div className="mt-8 text-right text-lg">
          <p>
            Total: <strong>${total.toLocaleString()}</strong>
          </p>
          <button className="mt-2 px-6 py-3 bg-teal-600 text-white rounded-lg hover:bg-teal-700 transition text-base">
            Proceder al pago
          </button>
        </div>
      </div>
  );
};

export default CartPage;
