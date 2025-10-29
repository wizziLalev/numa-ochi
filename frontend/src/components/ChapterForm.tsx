import React, { useState, useEffect } from 'react';
import { Chapter } from '../types';
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";

interface ChapterFormProps {
  initialData?: Chapter; // Optional: for editing existing chapter
  onSubmit: (chapter: Omit<Chapter, 'id'>) => void;
  onCancel: () => void;
}

const ChapterForm: React.FC<ChapterFormProps> = ({ initialData, onSubmit, onCancel }) => {
  const [title, setTitle] = useState(initialData?.title || '');
  const [filePath, setFilePath] = useState(initialData?.filePath || '');
  const [fileType, setFileType] = useState(initialData?.fileType || '');
  const [seriesId, setSeriesId] = useState<string>(initialData?.seriesId ? String(initialData.seriesId) : '');

  useEffect(() => {
    if (initialData) {
      setTitle(initialData.title || '');
      setFilePath(initialData.filePath || '');
      setFileType(initialData.fileType || '');
      setSeriesId(String(initialData.seriesId) || '');
    }
  }, [initialData]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({
      title,
      filePath,
      fileType,
      seriesId: Number(seriesId),
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 p-4 border rounded-lg shadow-md bg-white">
      <div className="space-y-2">
        <Label htmlFor="title">Title</Label>
        <Input
          id="title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="filePath">File Path</Label>
        <Input
          id="filePath"
          value={filePath}
          onChange={(e) => setFilePath(e.target.value)}
          required
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="fileType">File Type</Label>
        <Input
          id="fileType"
          value={fileType}
          onChange={(e) => setFileType(e.target.value)}
          required
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="seriesId">Series ID</Label>
        <Input
          type="number"
          id="seriesId"
          value={seriesId}
          onChange={(e) => setSeriesId(e.target.value)}
          required
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
          {initialData ? 'Update Chapter' : 'Create Chapter'}
        </Button>
      </div>
    </form>
  );
};

export default ChapterForm;
