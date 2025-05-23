// components/BaseModal.tsx
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
            className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-[1000]"
            onClick={onClose}
        >
            <div
                className={`relative rounded-lg p-6 ${className ?? "bg-white"} max-w-full`}
                onClick={(e) => e.stopPropagation()}
            >
                <button
                    className="absolute top-3 right-4 text-2xl text-white hover:text-red-500"
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
