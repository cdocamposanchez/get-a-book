export interface Book {
    id: string;
    title: string;
    publisher: string;
    description: string;
    imageUrl: string;
    year: number;
    quantity: number;
    price: number;
    qualification: number;
    categories: string;
    image: File | null;
}
