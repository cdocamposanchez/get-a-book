import type {UserRole} from "./userRole";

export interface User {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    role: UserRole;
    favorites: string[];
}
