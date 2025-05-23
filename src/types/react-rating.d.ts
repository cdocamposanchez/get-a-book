declare module 'react-rating' {
    import * as React from 'react';

    interface RatingProps {
        initialRating?: number;
        fractions?: number;
        readonly?: boolean;
        emptySymbol?: React.ReactNode;
        fullSymbol?: React.ReactNode;
        onChange?: (value: number) => void;
    }

    const Rating: React.FC<RatingProps>;
    export default Rating;
}
