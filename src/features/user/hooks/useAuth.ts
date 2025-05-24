export const useAuth = () => {
    const auth = JSON.parse(localStorage.getItem("auth") as string);
    return {
        token: auth?.token ?? null,
        userId: auth?.userId ?? null,
        userRole: auth?.userRole ?? null,
    };
};
