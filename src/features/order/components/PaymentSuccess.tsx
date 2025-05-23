import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import orderService from "../OrderService.ts";

const PaymentSuccess = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const finalizeOrder = async () => {
            const orderStr = localStorage.getItem("pendingOrder");
            if (!orderStr) return navigate("/");

            const order = JSON.parse(orderStr);
            try {
                await orderService.confirmOrder(order);
                localStorage.removeItem("cart");
                localStorage.removeItem("pendingOrder");
                navigate("/cart");
            } catch (error) {
                console.error("Error confirmando la orden", error);
                navigate("/");
            }
        };

        finalizeOrder();
    }, [navigate]);

    return (
        <main className="p-10 text-center">
            <h1 className="text-2xl font-bold">Confirmando tu orden...</h1>
        </main>
    );
};

export default PaymentSuccess;
