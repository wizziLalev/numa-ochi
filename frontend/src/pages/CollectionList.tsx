import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Collection } from '../types';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Button } from '../components/ui/button';

const CollectionList: React.FC = () => {
  const [collections, setCollections] = useState<Collection[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCollections = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await apiClient.get<Collection[]>('/api/collections');
        setCollections(response.data);
      } catch (err) {
        setError('Failed to fetch collections.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCollections();
  }, []); // Empty dependency array ensures this runs only once on mount

  if (loading) {
    return <div className="p-4">Loading collections...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Collection List</h1>
        <Link to="/collections/create">
          <Button>Add New Collection</Button>
        </Link>
      </div>

      {collections.length === 0 ? (
        <p>No collections found.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {collections.map((collection) => (
            <Link to={`/collections/${collection.id}`} key={collection.id} className="block">
              <Card className="hover:shadow-md transition-shadow">
                <CardHeader>
                  <CardTitle>{collection.name}</CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-sm">{collection.seriesIds?.length || 0} series</p>
                </CardContent>
              </Card>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
};

export default CollectionList;
