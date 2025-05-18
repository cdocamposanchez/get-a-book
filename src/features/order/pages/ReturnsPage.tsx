const ReturnsPage = () => {
  return (
      <div className="bg-[#7bb3b3] min-h-screen flex justify-center items-center">
        <div className="bg-[#e2e2e2] p-6 rounded-lg max-w-2xl w-full font-sans text-base text-[#333] shadow-md">
          <p className="mb-5">
            <em>
              Para iniciar el proceso de devolución debe contactarse al siguiente correo electrónico
              (<strong>costumerservice@getabook.com</strong>) con los siguientes documentos:
            </em>
          </p>
          <ul className="pl-5 mb-5 list-disc">
            <li className="mb-2"><strong>Captura de la orden.</strong></li>
            <li className="mb-2"><strong>Documento de identificación.</strong></li>
            <li className="mb-2"><strong>Texto explicando la razón de la devolución.</strong></li>
          </ul>
          <p>
            <em>
              Lamentamos que tengas que devolver uno de nuestros productos, estaremos listos para tu próxima compra.
            </em>
          </p>
        </div>
      </div>
  );
};

export default ReturnsPage;
