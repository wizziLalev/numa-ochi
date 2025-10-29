import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Series } from '../types';
import SeriesForm from '../components/SeriesForm';
import { useToast } from "../components/ui/use-toast";

const SeriesEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { toast } = useToast();
  const [series, setSeries] = useState<Series | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchSeries = async () => {
      if (!id) {
        setError("Series ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Series>(`/api/series/${id}`);
        setSeries(response.data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Failed to fetch series for editing.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchSeries();
  }, [id]);

  const handleSubmit = async (seriesData: Omit<Series, 'id'>) => {
    if (!id) return;
    try {
      await apiClient.put<Series>(`/api/series/${id}`, seriesData);
      toast({
        title: "Success!",
        description: "The series has been updated.",
      });
      navigate('/series');
    } catch (err: any) {
      toast({
        variant: "destructive",
        title: "Uh oh! Something went wrong.",
        description: "There was a problem with your request.",
      });
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/series');
  };

  if (loading) {
    return <div className="p-4">Loading series for editing...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!series) {
    return <div className="p-4">Series not found for editing.</div>;
  }

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Edit Series: {series.title}</h1>
      <SeriesForm initialData={series} onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default SeriesEdit;
