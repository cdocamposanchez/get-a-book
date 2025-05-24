import React from 'react'
import Rating from 'react-rating'

interface StarRatingProps {
    rating: number;
}

const StarRating: React.FC<StarRatingProps> = ({ rating }) => {
    return (
        <div className="flex items-center">
            <Rating
                readonly
                initialRating={rating}
                fractions={2}
                emptySymbol={<span className="text-gray-400 text-xl">★</span>}
                fullSymbol={<span className="text-yellow-400 text-xl">★</span>}
            />
            <span className="ml-2 text-black font-semibold text-md">
        ({rating.toFixed(1)})
      </span>
        </div>
    );
};

export default StarRating;
