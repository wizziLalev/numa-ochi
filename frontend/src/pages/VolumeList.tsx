import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Volume } from '../types';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '../components/ui/card';
import { Button } from '../components/ui/button';

const VolumeList: React.FC = () => {
  const [volumes, setVolumes] = useState<Volume[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchVolumes = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await apiClient.get<Volume[]>('/api/volumes');
        setVolumes(response.data);
      } catch (err) {
        setError('Failed to fetch volumes.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchVolumes();
  }, []); // Empty dependency array ensures this runs only once on mount

  if (loading) {
    return <div className="p-4">Loading volumes...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Volume List</h1>
        <Link to="/volumes/create">
          <Button>Add New Volume</Button>
        </Link>
      </div>

      {volumes.length === 0 ? (
        <p>No volumes found.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {volumes.map((volume) => (
            <Link to={`/volumes/${volume.id}`} key={volume.id} className="block">
              <Card className="hover:shadow-md transition-shadow">
                <CardHeader>
                  <CardTitle>{volume.title}</CardTitle>
                  <CardDescription>by {volume.author}</CardDescription>
                </CardHeader>
                <CardContent>
                  {volume.publicationDate && <p className="text-sm">Published: {volume.publicationDate}</p>}
                </CardContent>
              </Card>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
};

export default VolumeList;
