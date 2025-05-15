import { ApiResponse } from '@/types/common';
import { PopularYoutubeContent, PopularNextClassContent, PopularFullScoreContent } from '@/types/content'
import { fetchWithLangServer } from '@/lib/common/fetchWithLangServer'


export async function getPopularYoutubeContents(videoType: string): Promise<PopularYoutubeContent[]> {
  const res = await fetchWithLangServer(`${process.env.NEXT_PUBLIC_API_URL}/api/content/yt/popular/${videoType}`, {
    next: { revalidate: 60 }, // 60초 동안 캐시
  });

  if (!res.ok) {
    throw new Error('인기 유튜브 콘텐츠 불러오기 실패');
  }

  const result: ApiResponse<PopularYoutubeContent[]> = await res.json();

  if (result.code !== '200') {
    throw new Error(`API 응답 코드 오류: ${result.message}`);
  }

  return result.data; // 바로 DTO 리스트 반환
}

export async function getPopularNextclassContents(): Promise<PopularNextClassContent[]> {
  const res = await fetchWithLangServer(`${process.env.NEXT_PUBLIC_API_URL}/api/content/nc/popular`, {
    next: { revalidate: 60 }, // 60초 동안 캐시
  });

  if (!res.ok) {
    throw new Error('인기 강의 콘텐츠 불러오기 실패');
  }

  const result: ApiResponse<PopularNextClassContent[]> = await res.json();

  if (result.code !== '200') {
    throw new Error(`API 응답 코드 오류: ${result.message}`);
  }

  return result.data; // 바로 DTO 리스트 반환
}

export async function getPopularFullscoreContents(): Promise<PopularFullScoreContent[]> {
  const res = await fetchWithLangServer(`${process.env.NEXT_PUBLIC_API_URL}/api/content/fs/popular`, {
    next: { revalidate: 60 }, // 60초 동안 캐시
  });

  if (!res.ok) {
    throw new Error('인기 악보 콘텐츠 불러오기 실패');
  }

  const result: ApiResponse<PopularFullScoreContent[]> = await res.json();

  if (result.code !== '200') {
    throw new Error(`API 응답 코드 오류: ${result.message}`);
  }

  return result.data; // 바로 DTO 리스트 반환
}

