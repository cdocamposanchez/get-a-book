import {useEffect, useRef} from "react";
import { useNavigate } from "react-router-dom";
import orderService from "../OrderService.ts";
import {UseCart} from "../hooks/UseCart.ts";

const PaymentSuccess = () => {
    const navigate = useNavigate();
    const { clearCart } = UseCart();
    const finalizedRef = useRef(false);

    useEffect(() => {
        if (finalizedRef.current) return;
        finalizedRef.current = true;

        const finalizeOrder = async () => {
            const orderStr = localStorage.getItem("pendingOrder");
            if (!orderStr) return navigate("/");

            const order = JSON.parse(orderStr);
            try {
                await orderService.confirmOrder(order);
                localStorage.removeItem("cart");
                localStorage.removeItem("pendingOrder");
                clearCart();
                navigate("/cart");
            } catch (error) {
                console.error("Error confirmando la orden", error);
                navigate("/");
            }
        };

        finalizeOrder();
    }, [navigate, clearCart]);

    return (
        <main className="p-10 text-center">
            <h1 className="text-2xl font-bold">Confirmando tu orden...</h1>
        </main>
    );
};

export default PaymentSuccess;
