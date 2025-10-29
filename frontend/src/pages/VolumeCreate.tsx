import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Volume } from '../types';
import VolumeForm from '../components/VolumeForm';

const VolumeCreate: React.FC = () => {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (volumeData: Omit<Volume, 'id'>) => {
    try {
      await apiClient.post<Volume>('/api/volumes', volumeData);
      navigate('/volumes');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to create volume.');
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/volumes');
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Create New Volume</h1>
      {error && <div className="text-red-500 mb-4">Error: {error}</div>}
      <VolumeForm onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default VolumeCreate;
