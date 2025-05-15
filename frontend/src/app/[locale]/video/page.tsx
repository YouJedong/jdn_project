import { YoutubeContent } from '@/types/content';
import { getYoutubeContents } from '@/lib/api/content'

interface Props {
  searchParams: {
    page?: number;
    size?: number;
  }
  
}


export default async function VideoListPage({ searchParams } : Props) {
  const page = searchParams.page ?? 1;
  const size = searchParams.size ?? 10;

  const youtubeContentList = await getYoutubeContents(searchParams);

  return (
    <div className="px-6 py-10">
      <h1 className="text-2xl font-bold mb-4">영상 목록</h1>
    </div>
  )
}