import { useState, useEffect } from "react";
import type {CartItem} from "../../../types/cartItem";

export const UseCart = () => {
    const [itemsCount, setItemsCount] = useState(0);

    const calculateCount = () => {
        const cart = JSON.parse(localStorage.getItem("cart") ?? "[]") as CartItem[];
        setItemsCount(cart.length);
    };

    useEffect(() => {
        calculateCount();

        const handleStorageChange = () => {
            calculateCount();
        };

        window.addEventListener("storage", handleStorageChange);

        return () => {
            window.removeEventListener("storage", handleStorageChange);
        };
    }, []);

    const updateCart = () => {
        calculateCount();
        window.dispatchEvent(new Event("storage"));
    };

    const clearCart = () => {
        localStorage.setItem("cart", JSON.stringify([]));
        calculateCount();
        window.dispatchEvent(new Event("storage"));
    };

    return { itemsCount, updateCart, clearCart };
};
