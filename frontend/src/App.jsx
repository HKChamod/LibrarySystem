import React, { useState, useEffect } from 'react';
import { Routes, Route, Link, Navigate, useLocation } from 'react-router-dom';
import Login from './pages/Login';
import BookList from './pages/BookList';
import AdminDashboard from './pages/AdminDashboard';
import AuthService from './services/auth.service';

function App() {
  const [currentUser, setCurrentUser] = useState(undefined);
  const location = useLocation();

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user) {
      setCurrentUser(user);
    }
  }, []);

  const logOut = () => {
    AuthService.logout();
    setCurrentUser(undefined);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-white border-b border-gray-200 px-4 py-2.5 shadow-sm">
        <div className="container flex flex-wrap justify-between items-center mx-auto">
          <Link to={"/"} className="flex items-center">
            <span className="self-center text-xl font-semibold whitespace-nowrap text-blue-600">
              Library System
            </span>
          </Link>
          <div className="flex items-center space-x-4">
            {currentUser ? (
              <>
                <Link to={"/books"} className="text-gray-700 hover:text-blue-700 font-medium">Books</Link>
                {currentUser.roles.includes("ROLE_ADMIN") && (
                   <Link to={"/admin"} className="text-gray-700 hover:text-blue-700 font-medium">Admin</Link>
                )}
                <a href="/login" className="text-gray-700 hover:text-blue-700 font-medium" onClick={logOut}>
                  Logout
                </a>
              </>
            ) : (
              <Link to={"/login"} className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 focus:outline-none">
                Login
              </Link>
            )}
          </div>
        </div>
      </nav>

      <div className="container mx-auto px-4 mt-8">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Navigate to="/books" />} />
          <Route path="/books" element={<BookList />} /> 
          <Route path="/admin" element={currentUser && currentUser.roles.includes("ROLE_ADMIN") ? <AdminDashboard /> : <Navigate to="/login" />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
