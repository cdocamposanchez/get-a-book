import { useCart } from "../../book/hooks/useCart";


type Book = {
  id: number;
  title: string;
  price: number;
  image: string;
};

type BookCardProps = {
  book: Book;
};

export const BookCard = ({ book }: BookCardProps) => {
  const { addToCart } = useCart();

  return (
    <div className="book-card">
      <img src={book.image} alt={book.title} width={100} />
      <h3>{book.title}</h3>
      <p>${book.price}</p>
      <button
        onClick={() =>
          addToCart({
            id: book.id,
            title: book.title,
            price: book.price,
            quantity: 1,
            image: book.image,
          })
        }
      >
        Agregar al carrito
      </button>
    </div>
  );
};
