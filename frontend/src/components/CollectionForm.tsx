import React, { useState, useEffect } from 'react';
import { Collection } from '../types';
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";

interface CollectionFormProps {
  initialData?: Collection; // Optional: for editing existing collection
  onSubmit: (collection: Omit<Collection, 'id'>) => void;
  onCancel: () => void;
}

const CollectionForm: React.FC<CollectionFormProps> = ({ initialData, onSubmit, onCancel }) => {
  const [name, setName] = useState(initialData?.name || '');
  const [seriesIds, setSeriesIds] = useState<string>(initialData?.seriesIds ? initialData.seriesIds.join(',') : '');

  useEffect(() => {
    if (initialData) {
      setName(initialData.name || '');
      setSeriesIds(initialData.seriesIds ? initialData.seriesIds.join(',') : '');
    }
  }, [initialData]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({
      name,
      seriesIds: seriesIds ? seriesIds.split(',').map(Number) : undefined,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 p-4 border rounded-lg shadow-md bg-white">
      <div className="space-y-2">
        <Label htmlFor="name">Collection Name</Label>
        <Input
          id="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="seriesIds">Series IDs (comma-separated)</Label>
        <Input
          id="seriesIds"
          value={seriesIds}
          onChange={(e) => setSeriesIds(e.target.value)}
        />
      </div>
      <div className="flex justify-end space-x-2">
        <Button
          type="button"
          variant="outline"
          onClick={onCancel}
        >
          Cancel
        </Button>
        <Button type="submit">
          {initialData ? 'Update Collection' : 'Create Collection'}
        </Button>
      </div>
    </form>
  );
};

export default CollectionForm;
