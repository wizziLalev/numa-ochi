import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Series } from '../types';
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

const SeriesDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [series, setSeries] = useState<Series | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchSeriesDetail = async () => {
      if (!id) {
        setError("Series ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Series>(`/api/series/${id}`);
        setSeries(response.data);
      } catch (err) {
        setError('Failed to fetch series details.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchSeriesDetail();
  }, [id]);

  const handleDelete = async () => {
    try {
      await apiClient.delete(`/api/series/${id}`);
      navigate('/series'); // Redirect to series list after deletion
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to delete series.');
      console.error(err);
    }
  };

  if (loading) {
    return <div className="p-4">Loading series details...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!series) {
    return <div className="p-4">Series not found.</div>;
  }

  return (
    <div className="p-4">
      <Card>
        <CardHeader>
          <div className="flex justify-between items-center">
            <div>
              <CardTitle>{series.title}</CardTitle>
              <CardDescription>by {series.author}</CardDescription>
            </div>
            <div className="flex space-x-2">
              <Link to={`/series/${series.id}/edit`} className="mr-2">
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
                      This action cannot be undone. This will permanently delete this series and all of its associated data.
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
              {series.coverImage && (
                <img src={series.coverImage} alt={`${series.title} cover`} className="w-full h-auto rounded-lg shadow-md" />
              )}
            </div>
            <div className="md:col-span-2 space-y-2">
              {series.publicationDate && <p className="text-md text-gray-600">Published: {series.publicationDate}</p>}
              {series.publisher && <p className="text-md text-gray-600">Publisher: {series.publisher}</p>}
              {series.isbn && <p className="text-md text-gray-600">ISBN: {series.isbn}</p>}
              {series.description && (
                <div className="mt-4">
                  <h2 className="text-xl font-semibold">Description</h2>
                  <p className="text-gray-800">{series.description}</p>
                </div>
              )}
            </div>
          </div>
        </CardContent>
      </Card>

      <div className="mt-6">
        <h2 className="text-xl font-semibold mb-2">Chapters</h2>
        <Link to="/chapters/create">
          <Button>Add New Chapter</Button>
        </Link>
        {/* In a real application, you would fetch and display chapters related to this series here */}
        <p className="mt-2 text-gray-600">Chapters for this series will be listed here.</p>
      </div>
    </div>
  );
};

export default SeriesDetail;
