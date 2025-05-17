import { YoutubeContent } from '@/types/content';
import { getYoutubeContents } from '@/lib/api/content'

interface Props {
  searchParams: {
    page?: string;
    size?: string;
  }
  
}


export default async function VideoListPage({ searchParams } : Props) {
  const youtubeContentList = await getYoutubeContents(searchParams);

  return (
    <div className="px-6 py-10">
      <h1 className="text-2xl font-bold mb-4">영상 목록</h1>
    </div>
  )
}