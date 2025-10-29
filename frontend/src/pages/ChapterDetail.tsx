import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Chapter } from '../types';
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

const ChapterDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [chapter, setChapter] = useState<Chapter | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchChapterDetail = async () => {
      if (!id) {
        setError("Chapter ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Chapter>(`/api/chapters/${id}`);
        setChapter(response.data);
      } catch (err) {
        setError('Failed to fetch chapter details.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchChapterDetail();
  }, [id]);

  const handleDelete = async () => {
    try {
      await apiClient.delete(`/api/chapters/${id}`);
      navigate('/chapters'); // Redirect to chapter list after deletion
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to delete chapter.');
      console.error(err);
    }
  };

  if (loading) {
    return <div className="p-4">Loading chapter details...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!chapter) {
    return <div className="p-4">Chapter not found.</div>;
  }

  return (
    <div className="p-4">
      <Card>
        <CardHeader>
          <div className="flex justify-between items-center">
            <div>
              <CardTitle>{chapter.title}</CardTitle>
              <CardDescription>File Type: {chapter.fileType}</CardDescription>
            </div>
            <div className="flex space-x-2">
              <Link to={`/chapters/${chapter.id}/edit`}>
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
                      This action cannot be undone. This will permanently delete this chapter.
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
          <p className="text-md text-gray-600">File Path: {chapter.filePath}</p>
          <p className="text-md text-gray-600">Series ID: {chapter.seriesId}</p>
        </CardContent>
      </Card>
    </div>
  );
};

export default ChapterDetail;
