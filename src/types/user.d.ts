import type {UserRole} from "./userRole";

export interface User {
    id: string;
    firstName: string;
    lastNames: string;
    email: string;
    password: string;
    role: UserRole;
    favorites: string[];
}