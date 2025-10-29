import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Chapter } from '../types';
import ChapterForm from '../components/ChapterForm';

const ChapterCreate: React.FC = () => {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (chapterData: Omit<Chapter, 'id'>) => {
    try {
      await apiClient.post<Chapter>('/api/chapters', chapterData);
      navigate('/chapters');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to create chapter.');
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/chapters');
  };

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Create New Chapter</h1>
      {error && <div className="text-red-500 mb-4">Error: {error}</div>}
      <ChapterForm onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default ChapterCreate;
