import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Collection } from '../types';
import CollectionForm from '../components/CollectionForm';

const CollectionEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [collection, setCollection] = useState<Collection | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCollection = async () => {
      if (!id) {
        setError("Collection ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Collection>(`/api/collections/${id}`);
        setCollection(response.data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Failed to fetch collection for editing.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchCollection();
  }, [id]);

  const handleSubmit = async (collectionData: Omit<Collection, 'id'>) => {
    if (!id) return;
    try {
      await apiClient.put<Collection>(`/api/collections/${id}`, collectionData);
      navigate('/collections');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to update collection.');
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/collections');
  };

  if (loading) {
    return <div className="p-4">Loading collection for editing...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!collection) {
    return <div className="p-4">Collection not found for editing.</div>;
  }

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Edit Collection: {collection.name}</h1>
      <CollectionForm initialData={collection} onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default CollectionEdit;
