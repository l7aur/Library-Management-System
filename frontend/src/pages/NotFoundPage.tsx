const NotFoundPage = () => {
    return (
        <div style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100vh', // Make it take up the full viewport height
            fontFamily: 'sans-serif',
        }}>
            <h1 style={{
                fontSize: '3em',
                color: '#777',
                marginBottom: '10px',
            }}>Oops! Page Not Found</h1>
            <p style={{
                fontSize: '1.2em',
                color: '#777',
                textAlign: 'center',
                maxWidth: '400px',
            }}>
                The page you are looking for might not exist, has been removed,
                had its name changed, or is temporarily unavailable.
            </p>
        </div>
    );
};

export default NotFoundPage;