import React, { useState } from "react";
import BaseModal from "../../../components/Modal.tsx";
import type { Address } from "../../../types/address";
import type { Order } from "../../../types/order";
import type { CartItem } from "../../../types/cartItem";

interface CheckoutModalProps {
    isOpen: boolean;
    onClose: () => void;
    cartItems: CartItem[];
    onSubmit: (order: Order) => void;
}

const emptyAddress: Address = {
    firstName: "",
    lastName: "",
    emailAddress: "",
    addressLine: "",
    country: "",
    city: "",
    zipCode: "",
};

const CheckoutModal: React.FC<CheckoutModalProps> = ({
                                                         isOpen,
                                                         onClose,
                                                         cartItems,
                                                         onSubmit,
                                                     }) => {
    const [shippingAddress, setShippingAddress] = useState<Address>(emptyAddress);
    const [billingAddress, setBillingAddress] = useState<Address>(emptyAddress);
    const [errors, setErrors] = useState<{ [key: string]: boolean }>({});

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement>,
        type: "shipping" | "billing"
    ) => {
        const { name, value } = e.target;
        const updater = type === "shipping" ? setShippingAddress : setBillingAddress;
        updater((prev) => ({ ...prev, [name]: value }));
        setErrors((prev) => ({ ...prev, [`${type}-${name}`]: false }));
    };

    const validateFields = () => {
        const fields = Object.keys(emptyAddress);
        const newErrors: { [key: string]: boolean } = {};

        fields.forEach((field) => {
            if (!(shippingAddress as any)[field]) newErrors[`shipping-${field}`] = true;
            if (!(billingAddress as any)[field]) newErrors[`billing-${field}`] = true;
        });

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = () => {
        if (!validateFields()) return;

        const auth = JSON.parse(localStorage.getItem("auth") ?? "{}");
        const customerId = auth?.userId ?? "";

        const order: Order = {
            id: crypto.randomUUID(),
            customerId,
            shippingAddress,
            billingAddress,
            orderStatus: "PENDING",
            orderItems: cartItems.map((item) => ({
                id: "",
                orderId: "",
                bookId: item.id,
                title: item.title,
                quantity: item.quantity,
                price: item.price,
            })),
        };

        onSubmit(order);
        onClose();
    };

    const renderFields = (
        type: "shipping" | "billing",
        data: Address,
        title: string
    ) => (
        <div className="bg-gray-50 p-4 rounded-lg shadow border">
            <h3 className="text-lg font-semibold mb-4">{title}</h3>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                {Object.keys(emptyAddress).map((field) => {
                    const key = `${type}-${field}`;
                    return (
                        <input
                            key={key}
                            type="text"
                            name={field}
                            required
                            value={(data as any)[field]}
                            onChange={(e) => handleChange(e, type)}
                            placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                            className={`px-3 py-2 text-sm rounded border ${
                                errors[key] ? "border-red-500" : "border-gray-300"
                            } focus:outline-none focus:ring-2 focus:ring-green-400`}
                        />
                    );
                })}
            </div>
        </div>
    );

    return (
        <BaseModal isOpen={isOpen} onClose={onClose} className="bg-white w-[95vw] max-w-4xl">
            <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">
                Información de Envío y Facturación
            </h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {renderFields("shipping", shippingAddress, "Dirección de Envío")}
                {renderFields("billing", billingAddress, "Dirección de Facturación")}
            </div>

            <div className="text-right mt-6">
                <button
                    onClick={handleSubmit}
                    className="px-6 py-2 bg-green-600 text-black rounded hover:bg-green-700 hover:scale-105 transition-transform border border-black"
                >
                    Confirmar Pedido
                </button>
            </div>
        </BaseModal>
    );
};

export default CheckoutModal;
