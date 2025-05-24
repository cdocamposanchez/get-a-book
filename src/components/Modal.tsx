import React from "react";

interface BaseModalProps {
    isOpen: boolean;
    onClose: () => void;
    children: React.ReactNode;
    className?: string;
}

const BaseModal: React.FC<BaseModalProps> = ({ isOpen, onClose, children, className }) => {
    if (!isOpen) return null;

    return (
        <div
            className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-[1000] border-black border-1"
            onClick={onClose}
        >
            <div
                className={`relative rounded-lg p-6 border ${className ?? "bg-white"} max-w-full`}
                onClick={(e) => e.stopPropagation()}
            >
                <button
                    className="absolute top-0 right-4 text-2xl text-black hover:text-red-500 hover:scale-120 transform transition-transform"
                    onClick={onClose}
                >
                    ×
                </button>
                {children}
            </div>
        </div>
    );
};

export default BaseModal;
