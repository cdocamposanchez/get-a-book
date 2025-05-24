import axiosInstance from '../../utils/axiosConfig.ts';
import type { Response } from '../../types/response';
import type { Auth } from '../../types/auth/auth';
import type { User } from '../../types/user';
import type { Login } from '../../types/auth/login';
import type { Register } from '../../types/auth/register';
import type { UpdateEmailRequest } from '../../types/updateEmailRequest';

const USER_BASE = '/users';

export const userService = {
    login: async (credentials: Login): Promise<Auth> => {
        const response = await axiosInstance.post<Response<Auth>>(`${USER_BASE}/auth/login`, credentials);
        localStorage.setItem("auth", JSON.stringify(response.data.data));
        return response.data.data;
    },

    register: async (user: Register): Promise<Auth> => {
        const response = await axiosInstance.post<Response<Auth>>(`${USER_BASE}/auth/register`, user);
        localStorage.setItem("auth", JSON.stringify(response.data.data));
        return response.data.data;
    },

    updateUser: async (user: User): Promise<User> => {
        const response = await axiosInstance.put<Response<User>>(`${USER_BASE}`, user);
        return response.data.data;
    },

    updateFavorite: async (bookId: string): Promise<string> => {
        const response = await axiosInstance.put<Response<string>>(`${USER_BASE}/update-favorite`, null, {
            params: { bookId }
        });
        return response.data.data;
    },

    deleteUser: async (userId: string): Promise<void> => {
        await axiosInstance.delete<Response<User>>(`${USER_BASE}`, {
            params: { userId }
        });
    },

    getAllUsers: async (page = 0, size = 10): Promise<User[]> => {
        const response = await axiosInstance.get<Response<User[]>>(`${USER_BASE}`, {
            params: { page, size }
        });
        return response.data.data;
    },

    getUserById: async (id: string): Promise<User> => {
        const response = await axiosInstance.get<Response<User>>(`${USER_BASE}/${id}`);
        return response.data.data;
    },

    sendRecoveryCode: async (email: string): Promise<string> => {
        const response = await axiosInstance.post<Response<string>>(`${USER_BASE}/recover/send-code`, null, {
            params: { email }
        });
        return response.data.message;
    },

    validateRecoveryCode: async (email: string, recoverCode: string): Promise<boolean> => {
        const response = await axiosInstance.post<Response<boolean>>(`${USER_BASE}/recover/validate-code`, {
            email,
            recoverCode
        });
        return response.data.data;
    },

    changePassword: async (email: string, recoverCode: string, newPassword: string): Promise<string> => {
        const response = await axiosInstance.post<Response<string>>(`${USER_BASE}/recover/change-password`, {
            email,
            recoverCode,
            newPassword
        });
        return response.data.message;
    },

    changeEmail: async (data: UpdateEmailRequest): Promise<string> => {
        const response = await axiosInstance.post<Response<string>>(`${USER_BASE}/change-password`, data);
        return response.data.message;
    },

    validateToken: async (token: string): Promise<boolean> => {
        const response = await axiosInstance.get<Response<boolean>>(`${USER_BASE}/token/validate`, {
            params: { token }
        });
        return response.data.data;
    },

    getUserIdFromToken: async (token: string): Promise<string> => {
        const response = await axiosInstance.get<Response<string>>(`${USER_BASE}/token/user-id`, {
            params: { token }
        });
        return response.data.data;
    }
};
