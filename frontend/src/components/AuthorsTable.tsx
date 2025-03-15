import React, { useEffect, useState } from 'react';
import AuthorT from '../types/AuthorT.tsx';  // Assuming the AuthorT interface is in this file

const AuthorTable: React.FC = () => {
    // State to hold author data
    const [authors, setAuthors] = useState<AuthorT[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    // Fetching authors from backend
    useEffect(() => {
        const fetchAuthors = async () => {
            try {
                const response = await fetch('http://localhost:8080/authors');
                const data = await response.json();
                setAuthors(data);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching authors:', error);
                setLoading(false);
            }
        };

        fetchAuthors();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Alias</th>
                <th>Nationality</th>
                <th>Books</th>
            </tr>
            </thead>
            <tbody>
            {authors.map((author) => (
                <tr key={author.id}>
                    <td>{author.id}</td>
                    <td>{author.firstName}</td>
                    <td>{author.lastName}</td>
                    <td>{author.alias}</td>
                    <td>{author.nationality}</td>
                    <td>{author.books.join(', ')}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default AuthorTable;
