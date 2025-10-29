import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Collection } from '../types';
import CollectionForm from '../components/CollectionForm';

const CollectionCreate: React.FC = () => {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (collectionData: Omit<Collection, 'id'>) => {
    try {
      await apiClient.post<Collection>('/api/collections', collectionData);
      navigate('/collections');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to create collection.');
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/collections');
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Create New Collection</h1>
      {error && <div className="text-red-500 mb-4">Error: {error}</div>}
      <CollectionForm onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default CollectionCreate;
