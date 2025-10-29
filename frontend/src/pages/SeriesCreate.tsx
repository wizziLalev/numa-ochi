import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api/axiosConfig';
import { Series } from '../types';
import SeriesForm from '../components/SeriesForm';
import { useToast } from "../components/ui/use-toast";

const SeriesCreate: React.FC = () => {
  const navigate = useNavigate();
  const { toast } = useToast();

  const handleSubmit = async (seriesData: Omit<Series, 'id'>) => {
    try {
      await apiClient.post<Series>('/api/series', seriesData);
      toast({
        title: "Success!",
        description: "The new series has been created.",
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

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Create New Series</h1>
      <SeriesForm onSubmit={handleSubmit} onCancel={handleCancel} />
    </div>
  );
};

export default SeriesCreate;
