import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Chapter } from '../types';
import ChapterForm from '../components/ChapterForm';

const ChapterEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [chapter, setChapter] = useState<Chapter | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchChapter = async () => {
      if (!id) {
        setError("Chapter ID is missing.");
        setLoading(false);
        return;
      }
      try {
        const response = await apiClient.get<Chapter>(`/api/chapters/${id}`);
        setChapter(response.data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Failed to fetch chapter for editing.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchChapter();
  }, [id]);

  const handleSubmit = async (chapterData: Omit<Chapter, 'id'>) => {
    if (!id) return;
    try {
      await apiClient.put<Chapter>(`/api/chapters/${id}`, chapterData);
      navigate('/chapters');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to update chapter.');
      console.error(err);
    }
  };

  const handleCancel = () => {
    navigate('/chapters');
  };

  if (loading) {
    return <div className="p-4">Loading chapter for editing...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  if (!chapter) {
    return <div className="p-4">Chapter not found for editing.</div>;
  }

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Edit Chapter: {chapter.title}</h1>
      <ChapterForm initialData={chapter} onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default ChapterEdit;
