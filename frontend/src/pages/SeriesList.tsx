import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Series } from '../types';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Alert, AlertDescription, AlertTitle } from "../components/ui/alert";
import { Skeleton } from "../components/ui/skeleton";

const SeriesList: React.FC = () => {
  const [series, setSeries] = useState<Series[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState<string>('');

  useEffect(() => {
    const fetchSeries = async () => {
      setLoading(true);
      setError(null);
      try {
        const endpoint = searchQuery ? `/api/series/search?query=${searchQuery}` : '/api/series';
        const response = await apiClient.get<Series[]>(endpoint);
        setSeries(response.data);
      } catch (err) {
        setError('Failed to fetch series.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    if (searchQuery) {
      const timer = setTimeout(() => {
        fetchSeries();
      }, 500);
      return () => clearTimeout(timer);
    } else {
      fetchSeries();
    }
  }, [searchQuery]);

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
  };

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
        <h1 className="text-2xl font-bold">Series Library</h1>
        <Link to="/series/create">
          <Button>Add New Series</Button>
        </Link>
      </div>

      <div className="mb-4">
        <Input
          type="text"
          placeholder="Search series by title, author, ISBN..."
          value={searchQuery}
          onChange={handleSearchChange}
        />
      </div>

      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {Array.from({ length: 6 }).map((_, i) => (
            <Card key={i}>
              <CardHeader>
                <Skeleton className="h-6 w-3/4" />
                <Skeleton className="h-4 w-1/2" />
              </CardHeader>
              <CardContent>
                <Skeleton className="h-4 w-full" />
                <Skeleton className="h-4 w-full mt-2" />
              </CardContent>
            </Card>
          ))}
        </div>
      ) : series.length === 0 ? (
        <p>No series found. {searchQuery && "Try a different search term."}</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {series.map((s) => (
            <Link to={`/series/${s.id}`} key={s.id} className="block">
              <Card className="hover:shadow-md transition-shadow">
                <CardHeader>
                  <CardTitle>{s.title}</CardTitle>
                  <CardDescription>by {s.author}</CardDescription>
                </CardHeader>
                <CardContent>
                  {s.publicationDate && <p className="text-sm">Published: {s.publicationDate}</p>}
                  {s.description && <p className="text-sm mt-2 line-clamp-3">{s.description}</p>}
                </CardContent>
              </Card>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
};

export default SeriesList;
