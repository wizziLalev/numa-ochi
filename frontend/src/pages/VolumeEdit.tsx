import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Volume } from '../types';
import VolumeForm from '../components/VolumeForm';

const VolumeEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [volume, setVolume] = useState<Volume | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchVolume = async () => {
      if (!id) {
        setError("Volume ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Volume>(`/api/volumes/${id}`);
        setVolume(response.data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Failed to fetch volume for editing.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchVolume();
  }, [id]);

  const handleSubmit = async (volumeData: Omit<Volume, 'id'>) => {
    if (!id) return;
    try {
      await apiClient.put<Volume>(`/api/volumes/${id}`, volumeData);
      navigate('/volumes');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to update volume.');
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/volumes');
  };

  if (loading) {
    return <div className="p-4">Loading volume for editing...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!volume) {
    return <div className="p-4">Volume not found for editing.</div>;
  }

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Edit Volume: {volume.title}</h1>
      <VolumeForm initialData={volume} onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default VolumeEdit;
