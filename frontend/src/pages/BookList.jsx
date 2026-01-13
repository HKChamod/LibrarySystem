import React, { useState, useEffect } from 'react';
import api from '../services/api';
import AuthService from '../services/auth.service';

const BookList = () => {
    const [books, setBooks] = useState([]);
    const [query, setQuery] = useState('');
    const [isAdmin, setIsAdmin] = useState(false);
    
    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user && user.roles.includes("ROLE_ADMIN")) {
            setIsAdmin(true);
        }
        fetchBooks();
    }, []);

    const fetchBooks = async (searchQuery = '') => {
        try {
            const url = searchQuery ? `/books?query=${searchQuery}` : '/books';
            const response = await api.get(url);
            setBooks(response.data);
        } catch (error) {
            console.error("Error fetching books", error);
        }
    };

    const handleSearch = (e) => {
        e.preventDefault();
        fetchBooks(query);
    };

    const handleBorrow = async (bookId) => {
        // Borrow logic usually requires Admin in this prompt requirements: "Borrowing System... Admin Dashboard... View issued books"
        // But "Issue & return books" is listed under Copre Features.
        // Prompt says "User Roles: Admin, Librarian, Member". "Borrowing System: Issue & return books".
        // Usually Members can initiate borrow or Librarian issues it. 
        // Prompt says "Admin Dashboard... View issued books".
        // Let's assume Librarian/Admin must issue the book via API logic `issueBook` taking `userId`.
        // If this is member view, they might request. But stick to requirement: Admin/Librarian issues.
        // So for Member, just View.
        alert("Please ask a Librarian to issue this book.");
    };

    return (
        <div className="container mx-auto mt-10">
            <h2 className="text-3xl font-bold mb-6 text-gray-800">Library Catalog</h2>
            
            <form onSubmit={handleSearch} className="mb-8 flex gap-4">
                <input 
                    type="text" 
                    placeholder="Search by title, author..." 
                    className="flex-1 p-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 max-w-lg"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                />
                <button type="submit" className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 shadow-md transition">
                    Search
                </button>
            </form>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {books.map(book => (
                    <div key={book.id} className="bg-white p-6 rounded-xl shadow-md hover:shadow-xl transition duration-300 border border-gray-100">
                        <div className="flex justify-between items-start mb-4">
                            <div>
                                <h3 className="text-xl font-bold text-gray-900">{book.title}</h3>
                                <p className="text-gray-600">{book.author}</p>
                            </div>
                            <span className={`px-3 py-1 rounded-full text-xs font-semibold ${book.available ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                                {book.available ? 'Available' : 'Borrowed'}
                            </span>
                        </div>
                        <p className="text-gray-500 text-sm mb-4">ISBN: {book.isbn}</p>
                        {book.category && (
                            <p className="text-blue-500 text-sm font-medium mb-4">{book.category.name}</p>
                        )}
                        
                        {/* Only show borrow button if available (and maybe if user has role, but simplified) */}
                         <button 
                            disabled={!book.available}
                            onClick={() => handleBorrow(book.id)}
                            className={`w-full py-2 rounded-lg font-medium transition ${!book.available ? 'bg-gray-300 cursor-not-allowed text-gray-500' : 'bg-blue-600 text-white hover:bg-blue-700'}`}
                        >
                            {book.available ? 'Request Issue' : 'Unavailable'}
                        </button>
                    </div>
                ))}
            </div>
            
            {books.length === 0 && (
                <p className="text-center text-gray-500 mt-10">No books found.</p>
            )}
        </div>
    );
};

export default BookList;
