import { useAuth } from "../config/GlobalState.tsx";

function BuyPage() {
    const { isAuthenticated, user } = useAuth();

    return (
        <div className={styles.homePage}>

            <footer className={styles.siteFooter}>
                <p> 2025 L7aur's Bookshop. All rights reserved.</p>
            </footer>
        </div>
    );
}

export default BuyPage;