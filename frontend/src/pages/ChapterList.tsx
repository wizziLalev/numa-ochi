import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Chapter } from '../types';
import { Button } from '../components/ui/button';
import { Alert, AlertDescription, AlertTitle } from "../components/ui/alert";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "../components/ui/table"

const ChapterList: React.FC = () => {
  const [chapters, setChapters] = useState<Chapter[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchChapters = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await apiClient.get<Chapter[]>('/api/chapters');
        setChapters(response.data);
      } catch (err) {
        setError('Failed to fetch chapters.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchChapters();
  }, []); // Empty dependency array ensures this runs only once on mount

  if (loading) {
    return <div className="p-4">Loading chapters...</div>;
  }

  if (error) {
    return (
      <div className="p-4">
        <Alert variant="destructive">
          <AlertTitle>Error</AlertTitle>
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      </div>
    );
  }

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Chapter List</h1>
        <Link to="/chapters/create">
          <Button>Add New Chapter</Button>
        </Link>
      </div>

      <Table>
        <TableCaption>A list of your recent chapters.</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead>Title</TableHead>
            <TableHead>File Type</TableHead>
            <TableHead>Series ID</TableHead>
            <TableHead className="text-right">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {chapters.map((chapter) => (
            <TableRow key={chapter.id}>
              <TableCell className="font-medium">{chapter.title}</TableCell>
              <TableCell>{chapter.fileType}</TableCell>
              <TableCell>{chapter.seriesId}</TableCell>
              <TableCell className="text-right">
                <Link to={`/chapters/${chapter.id}`}>
                  <Button variant="outline">View</Button>
                </Link>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default ChapterList;
