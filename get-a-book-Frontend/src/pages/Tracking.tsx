import React from 'react';
import './Tracking.css';
import { FaBox, FaShippingFast, FaCheckCircle } from 'react-icons/fa';

function Tracking() {
  const etapas = [
    { nombre: 'Confirmado', icono: <FaBox />, activo: true },
    { nombre: 'Enviado', icono: <FaShippingFast />, activo: true },
    { nombre: 'Entregado', icono: <FaCheckCircle />, activo: false },
  ];

  return (
    <div className="tracking-container">
      <h1>Seguimiento de pedido</h1>
      <div className="tracking-steps">
        {etapas.map((etapa, index) => (
          <div className={`step ${etapa.activo ? 'active' : ''}`} key={index}>
            <div className="icon">{etapa.icono}</div>
            <p>{etapa.nombre}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Tracking;

