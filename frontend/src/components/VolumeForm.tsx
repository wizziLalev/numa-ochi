import React, { useState, useEffect } from 'react';
import { Volume } from '../types';
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Textarea } from "./ui/textarea";

interface VolumeFormProps {
  initialData?: Volume; // Optional: for editing existing volume
  onSubmit: (volume: Omit<Volume, 'id'>) => void;
  onCancel: () => void;
}

const VolumeForm: React.FC<VolumeFormProps> = ({ initialData, onSubmit, onCancel }) => {
  const [title, setTitle] = useState(initialData?.title || '');
  const [author, setAuthor] = useState(initialData?.author || '');
  const [publicationDate, setPublicationDate] = useState(initialData?.publicationDate || '');
  const [description, setDescription] = useState(initialData?.description || '');
  const [coverImage, setCoverImage] = useState(initialData?.coverImage || '');
  const [publisher, setPublisher] = useState(initialData?.publisher || '');
  const [isbn, setIsbn] = useState(initialData?.isbn || '');
  const [seriesId, setSeriesId] = useState<string>(initialData?.seriesId ? String(initialData.seriesId) : '');
  const [chapterIds, setChapterIds] = useState<string>(initialData?.chapterIds ? initialData.chapterIds.join(',') : '');

  useEffect(() => {
    if (initialData) {
      setTitle(initialData.title || '');
      setAuthor(initialData.author || '');
      setPublicationDate(initialData.publicationDate || '');
      setDescription(initialData.description || '');
      setCoverImage(initialData.coverImage || '');
      setPublisher(initialData.publisher || '');
      setIsbn(initialData.isbn || '');
      setSeriesId(initialData.seriesId ? String(initialData.seriesId) : '');
      setChapterIds(initialData.chapterIds ? initialData.chapterIds.join(',') : '');
    }
  }, [initialData]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({
      title,
      author,
      publicationDate,
      description,
      coverImage,
      publisher,
      isbn,
      seriesId: seriesId ? Number(seriesId) : undefined,
      chapterIds: chapterIds ? chapterIds.split(',').map(Number) : undefined,
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
        <Label htmlFor="author">Author</Label>
        <Input
          id="author"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          required
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="publicationDate">Publication Date</Label>
        <Input
          type="date"
          id="publicationDate"
          value={publicationDate}
          onChange={(e) => setPublicationDate(e.target.value)}
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="description">Description</Label>
        <Textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="coverImage">Cover Image URL</Label>
        <Input
          id="coverImage"
          value={coverImage}
          onChange={(e) => setCoverImage(e.target.value)}
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="publisher">Publisher</Label>
        <Input
          id="publisher"
          value={publisher}
          onChange={(e) => setPublisher(e.target.value)}
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="isbn">ISBN</Label>
        <Input
          id="isbn"
          value={isbn}
          onChange={(e) => setIsbn(e.target.value)}
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="seriesId">Series ID</Label>
        <Input
          type="number"
          id="seriesId"
          value={seriesId}
          onChange={(e) => setSeriesId(e.target.value)}
        />
      </div>
      <div className="space-y-2">
        <Label htmlFor="chapterIds">Chapter IDs (comma-separated)</Label>
        <Input
          id="chapterIds"
          value={chapterIds}
          onChange={(e) => setChapterIds(e.target.value)}
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
          {initialData ? 'Update Volume' : 'Create Volume'}
        </Button>
      </div>
    </form>
  );
};

export default VolumeForm;
