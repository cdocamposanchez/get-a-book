import axiosInstance from '../../utils/axiosConfig.ts';
import type {Response} from "../../types/response";
import type {Auth} from "../../types/auth/auth";
import type {User} from "../../types/user";
import type {Login} from "../../types/auth/login";
import type {Register} from "../../types/auth/register";


export const userService = {
    login: async (credentials: Login): Promise<Auth> => {
        const response = await axiosInstance.post<Response<Auth>>('/users/auth/login', credentials);
        localStorage.setItem("auth", JSON.stringify(response.data.data));
        return response.data.data;
    },

    register: async (user: Register): Promise<Auth> => {
        const response = await axiosInstance.post<Response<Auth>>('/users/auth/register', user);
        return response.data.data;
    },

    updateUser: async (user: User): Promise<User> => {
        const response = await axiosInstance.put<Response<User>>('/users', user);
        return response.data.data;
    },

    updateFavorite: async (bookId: string): Promise<string> => {
        const response = await axiosInstance.put<Response<string>>(`/users/update-favorite`, null, {
            params: { bookId }
        });
        return response.data.data;
    },

    deleteUser: async (userId: string): Promise<void> => {
        await axiosInstance.delete<Response<User>>(`/users`, {
            params: { userId }
        });
    },

    getAllUsers: async (page = 0, size = 10): Promise<User[]> => {
        const response = await axiosInstance.get<Response<User[]>>('/users', {
            params: { page, size }
        });
        return response.data.data;
    },

    getUserById: async (id: string): Promise<User> => {
        const response = await axiosInstance.get<Response<User>>(`/users/${id}`);
        return response.data.data;
    },

    sendRecoveryCode: async (email: string): Promise<string> => {
        const response = await axiosInstance.post<Response<string>>('/users/recover/send-code', null, {
            params: { email }
        });
        return response.data.message;
    },

    validateRecoveryCode: async (email: string, recoverCode: string): Promise<boolean> => {
        const response = await axiosInstance.post<Response<boolean>>('/users/recover/validate-code', {
            email,
            recoverCode
        });
        return response.data.data;
    },

    changePassword: async (email: string, recoverCode: string, newPassword: string): Promise<string> => {
        const response = await axiosInstance.post<Response<string>>('/users/recover/change-password', {
            email,
            recoverCode,
            newPassword
        });
        return response.data.message;
    },

    validateToken: async (token: string): Promise<boolean> => {
        const response = await axiosInstance.get<Response<boolean>>('/users/token/validate', {
            params: { token }
        });
        return response.data.data;
    },

    getUserIdFromToken: async (token: string): Promise<string> => {
        const response = await axiosInstance.get<Response<string>>('/users/token/user-id', {
            params: { token }
        });
        return response.data.data;
    }
};
