import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useNavigate } from 'react-router-dom';
import './globals.css';

import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import SeriesList from './pages/SeriesList';
import SeriesDetail from './pages/SeriesDetail';
import SeriesCreate from './pages/SeriesCreate';
import SeriesEdit from './pages/SeriesEdit';
import ChapterList from './pages/ChapterList';
import ChapterDetail from './pages/ChapterDetail';
import ChapterCreate from './pages/ChapterCreate';
import ChapterEdit from './pages/ChapterEdit';
import VolumeList from './pages/VolumeList';
import VolumeDetail from './pages/VolumeDetail';
import VolumeCreate from './pages/VolumeCreate';
import VolumeEdit from './pages/VolumeEdit';
import CollectionList from './pages/CollectionList';
import CollectionDetail from './pages/CollectionDetail';
import CollectionCreate from './pages/CollectionCreate';
import CollectionEdit from './pages/CollectionEdit';

import { AuthProvider, useAuth } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import { Toaster } from "./components/ui/toaster";

const AuthStatus: React.FC = () => {
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  if (isAuthenticated) {
    return (
      <button onClick={handleLogout} className="hover:underline">
        Logout
      </button>
    );
  }

  return (
    <div className="flex space-x-4">
      <Link to="/login" className="hover:underline">Login</Link>
      <Link to="/register" className="hover:underline">Register</Link>
    </div>
  );
};

function App() {
  return (
    <Router>
      <AuthProvider>
        <div className="min-h-screen bg-background text-foreground">
          <nav className="bg-primary text-primary-foreground p-4 shadow-md">
            <ul className="flex space-x-4 items-center">
              <li>
                <Link to="/" className="hover:underline">Home</Link>
              </li>
              <li>
                <Link to="/series" className="hover:underline">Series</Link>
              </li>
              <li>
                <Link to="/chapters" className="hover:underline">Chapters</Link>
              </li>
              <li>
                <Link to="/volumes" className="hover:underline">Volumes</Link>
              </li>
              <li>
                <Link to="/collections" className="hover:underline">Collections</Link>
              </li>
              <li className="ml-auto">
                <AuthStatus />
              </li>
            </ul>
          </nav>

          <main className="container mx-auto mt-4">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route
                path="/series"
                element={
                  <ProtectedRoute>
                    <SeriesList />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/series/create"
                element={
                  <ProtectedRoute>
                    <SeriesCreate />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/series/:id"
                element={
                  <ProtectedRoute>
                    <SeriesDetail />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/series/:id/edit"
                element={
                  <ProtectedRoute>
                    <SeriesEdit />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/chapters"
                element={
                  <ProtectedRoute>
                    <ChapterList />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/chapters/create"
                element={
                  <ProtectedRoute>
                    <ChapterCreate />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/chapters/:id"
                element={
                  <ProtectedRoute>
                    <ChapterDetail />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/chapters/:id/edit"
                element={
                  <ProtectedRoute>
                    <ChapterEdit />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/volumes"
                element={
                  <ProtectedRoute>
                    <VolumeList />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/volumes/create"
                element={
                  <ProtectedRoute>
                    <VolumeCreate />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/volumes/:id"
                element={
                  <ProtectedRoute>
                    <VolumeDetail />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/volumes/:id/edit"
                element={
                  <ProtectedRoute>
                    <VolumeEdit />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/collections"
                element={
                  <ProtectedRoute>
                    <CollectionList />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/collections/create"
                element={
                  <ProtectedRoute>
                    <CollectionCreate />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/collections/:id"
                element={
                  <ProtectedRoute>
                    <CollectionDetail />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/collections/:id/edit"
                element={
                  <ProtectedRoute>
                    <CollectionEdit />
                  </ProtectedRoute>
                }
              />
            </Routes>
          </main>
          <Toaster />
        </div>
      </AuthProvider>
    </Router>
  );
}

export default App;
