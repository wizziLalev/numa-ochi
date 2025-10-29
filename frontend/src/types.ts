export interface Series {
  id: number;
  title: string;
  author: string;
  publicationDate?: string; // ISO date string
  description?: string;
  coverImage?: string;
  publisher?: string;
  isbn?: string;
}

export interface Chapter {
  id: number;
  title: string;
  filePath: string;
  fileType: string;
  seriesId: number;
}

export interface Volume {
  id: number;
  title: string;
  author?: string;
  publicationDate?: string;
  description?: string;
  coverImage?: string;
  publisher?: string;
  isbn?: string;
  seriesId?: number;
  chapterIds?: number[];
}

export interface Collection {
  id: number;
  name: string;
  seriesIds?: number[];
}
