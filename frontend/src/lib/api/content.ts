import { ApiResponse } from '@/types/common';
import { PopularYoutubeContentDto } from '@/types/content'


export async function getPopularYoutubeContents(videoType: string): Promise<PopularYoutubeContentDto[]> {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/content/yt/popular/${videoType}`, {
    next: { revalidate: 60 }, // 60초 동안 캐시
  });

  if (!res.ok) {
    throw new Error('인기 유튜브 콘텐츠 불러오기 실패');
  }

  const result: ApiResponse<PopularYoutubeContentDto[]> = await res.json();

  if (result.code !== '200') {
    throw new Error(`API 응답 코드 오류: ${result.message}`);
  }

  return result.data; // 바로 DTO 리스트 반환
}