import React, { useState, useEffect } from 'react';
import api from '../services/api';

const AdminDashboard = () => {
    const [borrows, setBorrows] = useState([]);
    const [users, setUsers] = useState([]); // Fetch users logic needed in User Controller presumably
    const [activeTab, setActiveTab] = useState('borrows');

    // Simple Book Form State
    const [newBook, setNewBook] = useState({ title: '', author: '', isbn: '', categoryId: 1 });

    const [topBooks, setTopBooks] = useState([]);
    
    useEffect(() => {
        fetchBorrows();
        fetchUsers();
        fetchTopBooks();
    }, []);

    const fetchBorrows = async () => {
        try {
            const res = await api.get('/borrows');
            setBorrows(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchUsers = async () => {
        try {
            const res = await api.get('/users');
            setUsers(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchTopBooks = async () => {
        try {
            const res = await api.get('/books/top');
            setTopBooks(res.data);
        } catch (err) {
            console.error(err);
        }
    };
    
    // Add Book
    const handleAddBook = async (e) => {
        e.preventDefault();
        try {
            await api.post('/books', newBook);
            alert("Book added!");
            setNewBook({ title: '', author: '', isbn: '', categoryId: 1 });
        } catch (err) {
            alert("Failed to add book");
        }
    };

    const handleReturn = async (borrowId) => {
        try {
            await api.post(`/borrows/return/${borrowId}`);
            fetchBorrows();
            alert("Book returned successfully");
        } catch (err) {
            alert("Error returning book");
        }
    };

    return (
        <div className="container mx-auto mt-10 p-6 bg-white rounded-xl shadow-lg">
            <h2 className="text-3xl font-bold mb-8 text-gray-800 border-b pb-4">Admin Dashboard</h2>
            
            <div className="flex space-x-4 mb-8">
                <button 
                    onClick={() => setActiveTab('borrows')}
                    className={`px-4 py-2 rounded-lg font-medium ${activeTab === 'borrows' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
                >
                    Issued Books
                </button>
                <button 
                    onClick={() => setActiveTab('addBook')}
                    className={`px-4 py-2 rounded-lg font-medium ${activeTab === 'addBook' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
                >
                    Add Book
                </button>
                <button 
                    onClick={() => setActiveTab('users')}
                    className={`px-4 py-2 rounded-lg font-medium ${activeTab === 'users' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
                >
                    Users
                </button>
                <button 
                    onClick={() => setActiveTab('reports')}
                    className={`px-4 py-2 rounded-lg font-medium ${activeTab === 'reports' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'}`}
                >
                    Reports
                </button>
            </div>

            {activeTab === 'borrows' && (
                <div className="overflow-x-auto">
                    <table className="min-w-full leading-normal">
                        <thead>
                            <tr>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Book</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">User</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Due Date</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Status</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {borrows.map(b => (
                                <tr key={b.id}>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <p className="text-gray-900 whitespace-no-wrap">{b.book.title}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <p className="text-gray-900 whitespace-no-wrap">{b.user.username}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <p className="text-gray-900 whitespace-no-wrap">{b.dueDate}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <span className={`relative inline-block px-3 py-1 font-semibold leading-tight ${
                                            b.returnDate ? 'text-green-900' : 
                                            (new Date(b.dueDate) < new Date() ? 'text-red-900' : 'text-blue-900')
                                        }`}>
                                            <span aria-hidden className={`absolute inset-0 opacity-50 rounded-full ${
                                                b.returnDate ? 'bg-green-200' : 
                                                (new Date(b.dueDate) < new Date() ? 'bg-red-200' : 'bg-blue-200')
                                            }`}></span>
                                            <span className="relative">
                                                {b.returnDate ? 'Returned' : (new Date(b.dueDate) < new Date() ? 'Overdue' : 'Issued')}
                                            </span>
                                        </span>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        {!b.returnDate && (
                                            <button 
                                                onClick={() => handleReturn(b.id)}
                                                className="text-blue-600 hover:text-blue-900"
                                            >
                                                Return
                                            </button>
                                        )}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {activeTab === 'users' && (
                <div className="overflow-x-auto">
                    <table className="min-w-full leading-normal">
                        <thead>
                            <tr>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">ID</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Username</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Email</th>
                                <th className="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Roles</th>
                            </tr>
                        </thead>
                        <tbody>
                            {users.map(u => (
                                <tr key={u.id}>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <p className="text-gray-900 whitespace-no-wrap">{u.id}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <p className="text-gray-900 whitespace-no-wrap">{u.username}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <p className="text-gray-900 whitespace-no-wrap">{u.email}</p>
                                    </td>
                                    <td className="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                        <div className="flex flex-wrap gap-1">
                                            {u.roles.map(r => (
                                                <span key={r.id} className="px-2 py-1 text-xs font-semibold bg-gray-200 rounded-full">
                                                    {r.name}
                                                </span>
                                            ))}
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {activeTab === 'addBook' && (
                <div className="max-w-lg">
                    <form onSubmit={handleAddBook} className="space-y-4">
                        <div>
                            <label className="block text-gray-700">Title</label>
                            <input type="text" className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500" required
                                value={newBook.title} onChange={e => setNewBook({...newBook, title: e.target.value})} />
                        </div>
                        <div>
                            <label className="block text-gray-700">Author</label>
                            <input type="text" className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500" required
                                value={newBook.author} onChange={e => setNewBook({...newBook, author: e.target.value})} />
                        </div>
                        <div>
                            <label className="block text-gray-700">ISBN</label>
                            <input type="text" className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500" required
                                value={newBook.isbn} onChange={e => setNewBook({...newBook, isbn: e.target.value})} />
                        </div>
                        <button type="submit" className="w-full bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                            Add Book
                        </button>
                    </form>
                </div>
            )}
            
            {activeTab === 'reports' && (
                <div className="space-y-6">
                    <div>
                        <h3 className="text-xl font-bold mb-4">Most Borrowed Books</h3>
                        <ul className="list-disc list-inside bg-gray-50 p-4 rounded-lg">
                            {topBooks.map(b => (
                                <li key={b.id} className="text-gray-800">{b.title} by {b.author}</li>
                            ))}
                            {topBooks.length === 0 && <p>No data yet.</p>}
                        </ul>
                    </div>
                </div>
            )}
        </div>
    );
};

export default AdminDashboard;
