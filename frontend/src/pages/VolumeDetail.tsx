import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Volume } from '../types';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '../components/ui/card';
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

const VolumeDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [volume, setVolume] = useState<Volume | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchVolumeDetail = async () => {
      if (!id) {
        setError("Volume ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Volume>(`/api/volumes/${id}`);
        setVolume(response.data);
      } catch (err) {
        setError('Failed to fetch volume details.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchVolumeDetail();
  }, [id]);

  const handleDelete = async () => {
    try {
      await apiClient.delete(`/api/volumes/${id}`);
      navigate('/volumes'); // Redirect to volume list after deletion
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to delete volume.');
      console.error(err);
    }
  };

  if (loading) {
    return <div className="p-4">Loading volume details...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!volume) {
    return <div className="p-4">Volume not found.</div>;
  }

  return (
    <div className="p-4">
      <Card>
        <CardHeader>
          <div className="flex justify-between items-center">
            <div>
              <CardTitle>{volume.title}</CardTitle>
              <CardDescription>by {volume.author}</CardDescription>
            </div>
            <div className="flex space-x-2">
              <Link to={`/volumes/${volume.id}/edit`}>
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
                      This action cannot be undone. This will permanently delete this volume.
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
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="md:col-span-1">
              {volume.coverImage && (
                <img src={volume.coverImage} alt={`${volume.title} cover`} className="w-full h-auto rounded-lg shadow-md" />
              )}
            </div>
            <div className="md:col-span-2 space-y-2">
              {volume.publicationDate && <p className="text-md text-gray-600">Published: {volume.publicationDate}</p>}
              {volume.publisher && <p className="text-md text-gray-600">Publisher: {volume.publisher}</p>}
              {volume.isbn && <p className="text-md text-gray-600">ISBN: {volume.isbn}</p>}
              {volume.description && (
                <div className="mt-4">
                  <h2 className="text-xl font-semibold">Description</h2>
                  <p className="text-gray-800">{volume.description}</p>
                </div>
              )}
              {volume.seriesId && <p className="text-md text-gray-600 mt-4">Series ID: {volume.seriesId}</p>}
              {volume.chapterIds && volume.chapterIds.length > 0 && (
                <div className="mt-4">
                  <h2 className="text-xl font-semibold">Chapters in this Volume</h2>
                  <ul>
                    {volume.chapterIds.map((chapterId) => (
                      <li key={chapterId}>Chapter ID: {chapterId}</li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default VolumeDetail;
