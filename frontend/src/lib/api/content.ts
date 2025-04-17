import { HomeContent } from '@/types/content';

type ResultMap<T> = {
  code: string;
  data: T;
};


export async function getHomeContent(): Promise<HomeContent> {
  const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/content/home`, {
    next: { revalidate: 60 }, // or cache: 'no-store'
  });

  if (!res.ok) {
    throw new Error('홈 콘텐츠 불러오기 실패');
  }

  const result: ResultMap<HomeContent> = await res.json();

  if (result.code !== '200') {
    throw new Error('홈 콘텐츠 응답 코드 오류');
  }

  console.log("머닝",result.data);

  return result.data;
}