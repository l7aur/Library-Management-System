import React, { createContext, useContext, useState, ReactNode } from 'react';

interface GlobalState {
    isAuthenticated: boolean;
    globalUsername: string;
    setAuthenticated: (isAuthenticated: boolean) => void;
    setGlobalUsername: (username: string) => void;
}

const GlobalStateContext = createContext<GlobalState | undefined>(undefined);

export const GlobalStateProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [globalUsername, setGlobalUsername] = useState<string>("");

    return (
        <GlobalStateContext.Provider
            value={{
                isAuthenticated,
                globalUsername,
                setAuthenticated: setIsAuthenticated,
                setGlobalUsername,
            }}
        >
            {children}
        </GlobalStateContext.Provider>
    );
};

export const useGlobalState = (): GlobalState => {
    const context = useContext(GlobalStateContext);
    if (!context) {
        throw new Error('useGlobalState must be used within a GlobalStateProvider');
    }
    return context;
};
