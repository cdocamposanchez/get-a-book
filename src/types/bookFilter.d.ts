export interface BookFilter {
    category?: string;
    publisher?: string;
    year?: number;
    titleRegex?: string;
    minPrice?: number;
    maxPrice?: number;
    sortOrder?: 'ASC' | 'DESC';
    page?: number;
    size?: number;
}
