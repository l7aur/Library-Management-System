import { useAuth } from "../config/GlobalState.tsx";
import styles from "./HomePage.module.css"; // Import a CSS module

function HomePage() {
    const { isAuthenticated, user } = useAuth();

    return (
        <div className={styles.homePage}>
            {!isAuthenticated ? (
                <section className={styles.welcomeSection}>
                    <h1 className={styles.mainTitle}>Welcome to L7aur's Bookshop!</h1>
                    <p className={styles.tagline}>Discover your next favorite story.</p>
                    <button className={styles.callToAction}>Explore Our Collection</button>
                </section>
            ) : (
                <section className={styles.userSection}>
                    <h1 className={styles.greeting}>Hello, {user?.username || "User"}!</h1>
                    <p className={styles.welcomeBack}>Welcome back. We're delighted to have you here.</p>
                    <div className={styles.userActions}>
                        <button className={styles.viewProfile}>View Your Profile</button>
                        <button className={styles.viewOrders}>Your Order History</button>
                    </div>
                </section>
            )}
            <footer className={styles.siteFooter}>
                <p> 2025 L7aur's Bookshop. All rights reserved.</p>
            </footer>
        </div>
    );
}

export default HomePage;