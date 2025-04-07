import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import MyNavbar from "./components/MyNavbar";
import HomePage from "./pages/HomePage.tsx";
import BooksPage from "./pages/BooksPage.tsx";
import AuthorsPage from "./pages/AuthorsPage.tsx";
import PublishersPage from "./pages/PublishersPage.tsx";
import CartPage from "./pages/CartPage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import RegisterPage from "./pages/RegisterPage.tsx";
import NotFoundPage from "./pages/NotFoundPage.tsx";
import AppUsersPage from "./pages/AppUsersPage.tsx";
import {
    APP_USERS_PATH, AUTHORS_PATH, BOOKS_PATH, CART_PATH, HOME_PATH,
    LOGIN_PATH, NOT_FOUND_PATH, PUBLISHERS_PATH, REGISTER_PATH
} from "./constants/Paths.ts";
import { AuthProvider, ProtectedRoute } from './config/globalState'; // Adjust the import path

function App() {
    return (
        <AuthProvider> {/* Wrap the entire routing structure */}
            <div className="App">
                <MyNavbar />
                <Router>
                    <Routes>
                        <Route path={LOGIN_PATH} element={<LoginPage />} />
                        <Route path={REGISTER_PATH} element={<RegisterPage />} />
                        <Route path={NOT_FOUND_PATH} element={<NotFoundPage />} />

                        {/* Protected Routes */}
                        <Route
                            path={HOME_PATH}
                            element={
                                <ProtectedRoute>
                                    <HomePage />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path={BOOKS_PATH}
                            element={
                                <ProtectedRoute>
                                    <BooksPage />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path={AUTHORS_PATH}
                            element={
                                <ProtectedRoute>
                                    <AuthorsPage />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path={PUBLISHERS_PATH}
                            element={
                                <ProtectedRoute>
                                    <PublishersPage />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path={APP_USERS_PATH}
                            element={
                                <ProtectedRoute>
                                    <AppUsersPage />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path={CART_PATH}
                            element={
                                <ProtectedRoute>
                                    <CartPage />
                                </ProtectedRoute>
                            }
                        />

                        {/* Redirect any other unmatched route to NotFoundPage */}
                        <Route path="*" element={<Navigate to={NOT_FOUND_PATH} />} />
                    </Routes>
                </Router>
            </div>
        </AuthProvider>
    );
}

export default App;