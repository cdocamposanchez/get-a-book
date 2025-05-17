// src/pages/ReturnsPage.tsx
import React from 'react';
import './Returns.css';

const ReturnsPage = () => {
  return (
    <div className="returns-container">
      <div className="returns-box">
        <p><em>Para iniciar el proceso de devolución debe contactarse al siguiente correo electrónico (<strong>costumerservice@getabook.com</strong>) con los siguientes documentos:</em></p>
        <ul>
          <li><strong>Captura de la orden.</strong></li>
          <li><strong>Documento de identificación.</strong></li>
          <li><strong>Texto explicando la razón de la devolución.</strong></li>
        </ul>
        <p><em>Lamentamos que tengas que devolver uno de nuestros productos, estaremos listos para tu próxima compra.</em></p>
      </div>
    </div>
  );
};

export default ReturnsPage;
