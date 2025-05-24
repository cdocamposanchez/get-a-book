import React, {useState, type ChangeEvent, type FormEvent, useRef, useEffect} from "react";
import type { Book } from "../../../types/book";

interface BookFormProps {
    book?: Partial<Book>;
    onSubmit: (bookData: Omit<Book, "id" | "imageUrl">) => void;
    onCancel?: () => void;
    formError?: string | null;
}

const BookForm: React.FC<BookFormProps> = ({ book, onSubmit, onCancel, formError }) => {
    const [title, setTitle] = useState(book?.title ?? "");
    const [publisher, setPublisher] = useState(book?.publisher ?? "");
    const [description, setDescription] = useState(book?.description ?? "");
    const [year, setYear] = useState(book?.year ?? new Date().getFullYear());
    const [quantity, setQuantity] = useState(book?.quantity ?? 0);
    const [price, setPrice] = useState(book?.price ?? 0);
    const [qualification] = useState(book?.qualification ?? 0);
    const [categories, setCategories] = useState(book?.categories ?? "");
    const [image, setImage] = useState<File | null>(null);

    const descriptionRef = useRef<HTMLTextAreaElement>(null);

    const adjustTextareaHeight = () => {
        if (descriptionRef.current) {
            descriptionRef.current.style.height = "auto";
            descriptionRef.current.style.height = descriptionRef.current.scrollHeight + "px";
        }
    };

    useEffect(() => {
        adjustTextareaHeight();
    }, [description]);

    const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            setImage(e.target.files[0]);
        }
    };

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();
        onSubmit({
            title,
            publisher,
            description,
            year,
            quantity,
            price,
            qualification,
            categories,
            image,
        });
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-2 max-w-md">
            {formError && (
                <p className="text-red-600 text-sm mt-1">{formError}</p>
            )}
            <div>
                <label className="block font-medium mb-0.5 text-sm" htmlFor="title">Título</label>
                <input
                    id="title"
                    type="text"
                    value={title}
                    onChange={e => setTitle(e.target.value)}
                    required
                    className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                />
            </div>

            <div>
                <label className="block font-medium mb-0.5 text-sm" htmlFor="publisher">Autor / Editorial</label>
                <input
                    id="publisher"
                    type="text"
                    value={publisher}
                    onChange={e => setPublisher(e.target.value)}
                    required
                    className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                />
            </div>

            <div>
                <label className="block font-medium mb-0.5 text-sm" htmlFor="description">Descripción</label>
                <textarea
                    id="description"
                    ref={descriptionRef}
                    value={description}
                    onChange={e => setDescription(e.target.value)}
                    rows={2}
                    className="w-full border rounded px-2 py-1 text-sm resize-none hover:scale-105 transition-transform"
                    required
                />
            </div>

            <div className="grid grid-cols-3 gap-2">
                <div>
                    <label className="block font-medium mb-0.5 text-sm" htmlFor="year">Año</label>
                    <input
                        id="year"
                        type="number"
                        value={year}
                        onChange={e => setYear(Number(e.target.value))}
                        min={0}
                        className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                        required
                    />
                </div>

                <div>
                    <label className="block font-medium mb-0.5 text-sm" htmlFor="quantity">Cantidad</label>
                    <input
                        id="quantity"
                        type="number"
                        value={quantity}
                        onChange={e => setQuantity(Number(e.target.value))}
                        min={0}
                        className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                        required
                    />
                </div>

                <div>
                    <label className="block font-medium mb-0.5 text-sm" htmlFor="price">Precio</label>
                    <input
                        id="price"
                        type="number"
                        value={price}
                        inputMode="numeric"
                        pattern="\d*"
                        onChange={e => setPrice(Number(e.target.value))}
                        min={0}
                        step={0.01}
                        className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                        required
                    />
                </div>
            </div>


            <div>
                <label className="block font-medium mb-0.5 text-sm" htmlFor="categories">Categorías</label>
                <input
                    id="categories"
                    type="text"
                    value={categories}
                    onChange={e => setCategories(e.target.value)}
                    className="w-full border rounded px-2 py-1 text-sm hover:scale-105 transition-transform"
                    required
                />
            </div>

            <div className="mb-4">
                <label htmlFor="image" className="block text-sm font-medium text-gray-700 mb-1">
                    Imagen (subir archivo .jpg, .png)
                </label>
                <div className="flex items-center gap-4">
                    <input
                        id="image"
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                        className="block w-full text-sm text-gray-700
                                 file:mr-4 file:py-2 file:px-4
                                 file:rounded-lg file:border-0
                                 file:text-sm file:font-semibold
                                 file:bg-blue-50 file:text-blue-700
                                 hover:file:bg-blue-100 transition-colors
                                 hover:scale-105"
                    />
                </div>
                {image && (
                    <p className="mt-1 text-xs text-gray-500 italic">Archivo seleccionado: {image.name}</p>
                )}
            </div>

            <div className="flex gap-2">
                <button
                    type="submit"
                    className="px-3 py-1.5 bg-blue-600 text-white rounded hover:bg-blue-700 text-sm hover:scale-105 transition-transform"
                >
                    Guardar
                </button>
                {onCancel && (
                    <button
                        type="button"
                        onClick={onCancel}
                        className="px-3 py-1.5 bg-gray-300 rounded hover:bg-gray-400 text-sm hover:scale-105 transition-transform"
                    >
                        Cancelar
                    </button>
                )}
            </div>
        </form>
    );
};

export default BookForm;
