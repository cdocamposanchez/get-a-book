const Spinner = () => {
    return (
        <div className="flex justify-center items-center h-full backdrop-blur-xs">
            <div className="w-12 h-12 border-4 border-green-600 border-t-transparent rounded-full animate-spin"></div>
        </div>
    );
};

export default Spinner;
