import '../styles/CatalogPage.css';
import BookCard from "../components/BookCard.tsx";
import BookFilters from "../components/BookFilters.tsx";

const CatalogPage = () => {


  return (
    <div className="home-container">

      <BookFilters />

      <main className="books-area">
        <BookCard />
      </main>
    </div>
  );
};

export default CatalogPage;
