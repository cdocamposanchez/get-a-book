const HelpPage = () => {
    return (
        <div className="min-h-screen bg-gray-100 py-8 px-4">
            <div className="max-w-lg mx-auto bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-semibold text-center mb-6 text-gray-800">Instrucciones de Devolución</h2>
                <p className="text-gray-600 mb-4">
                    Si necesitas devolver un producto, sigue las instrucciones a continuación:
                </p>
                <ol className="list-decimal pl-6 text-gray-600">
                    <li>Comunícate con el servicio al cliente a través de: <strong>soporte@get-a-book.com</strong></li>
                    <li>Incluye en el correo electrónico los detalles de tu pedido (número de pedido, fecha, razón de la devolución).</li>
                    <li>Adjunta cualquier documento necesario, como un comprobante de pago o identificación.</li>
                    <li>Esperamos poder ayudarte y procesar tu solicitud lo más rápido posible.</li>
                </ol>
            </div>
        </div>
    );
};

export default HelpPage;
