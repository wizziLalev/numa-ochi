import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Collection } from '../types';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Button } from '../components/ui/button';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "../components/ui/alert-dialog"

const CollectionDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [collection, setCollection] = useState<Collection | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCollectionDetail = async () => {
      if (!id) {
        setError("Collection ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Collection>(`/api/collections/${id}`);
        setCollection(response.data);
      } catch (err) {
        setError('Failed to fetch collection details.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCollectionDetail();
  }, [id]);

  const handleDelete = async () => {
    try {
      await apiClient.delete(`/api/collections/${id}`);
      navigate('/collections'); // Redirect to collection list after deletion
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to delete collection.');
      console.error(err);
    }
  };

  if (loading) {
    return <div className="p-4">Loading collection details...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!collection) {
    return <div className="p-4">Collection not found.</div>;
  }

  return (
    <div className="p-4">
      <Card>
        <CardHeader>
          <div className="flex justify-between items-center">
            <CardTitle>{collection.name}</CardTitle>
            <div className="flex space-x-2">
              <Link to={`/collections/${collection.id}/edit`}>
                <Button variant="outline">Edit</Button>
              </Link>
              <AlertDialog>
                <AlertDialogTrigger asChild>
                  <Button variant="destructive">Delete</Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
                    <AlertDialogDescription>
                      This action cannot be undone. This will permanently delete this collection.
                    </AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                    <AlertDialogAction onClick={handleDelete}>Continue</AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          {collection.seriesIds && collection.seriesIds.length > 0 ? (
            <div>
              <h2 className="text-xl font-semibold">Series in this Collection</h2>
              <ul>
                {collection.seriesIds.map((seriesId) => (
                  <li key={seriesId}>Series ID: {seriesId}</li>
                ))}
              </ul>
            </div>
          ) : (
            <p>No series in this collection.</p>
          )}
        </CardContent>
      </Card>
    </div>
  );
};

export default CollectionDetail;
