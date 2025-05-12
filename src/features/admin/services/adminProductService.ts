import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/admin/products';

export const getAllProducts = async () => {
    const response = await axios.get(API_BASE);
    return response.data;
};

export const deleteProduct = async (id: number) => {
    const response = await axios.delete(`${API_BASE}/${id}`);
    return response.data;
};
