export type YoutubeContent = {
  id: number;
  title: string;
  videoId: string;
  thumbnailUrl: string;
};

export type ScoreContent = {
  id: number;
  title: string;
  platformUrl: string;
  thumbnailUrl: string;
};

export type LectureContent = {
  id: number;
  title: string;
  platformUrl: string;
  thumbnailUrl: string;
};

export type HomeContent = {
  youtubeContents: YoutubeContent[];
  scoreContents: ScoreContent[];
  lectureContents: LectureContent[];
};