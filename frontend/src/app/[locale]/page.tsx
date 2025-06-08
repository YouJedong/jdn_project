import { getTranslations } from 'next-intl/server';
import { routing } from '@/i18n/routing';
import { getPopularYoutubeContents, getPopularNextclassContents, getPopularFullscoreContents } from '@/lib/api/content';
import PopularYoutubeTab from './_components/PopularYoutubeTab';
import Image from 'next/image';
import Link from 'next/link';

export async function generateStaticParams() {
  return routing.locales.map((locale) => ({locale}));
}

export default async function HomePage({params}: {params: { locale: string }}) {
  const t = await getTranslations();
  const { locale } = await params

  const [performanceData, lessonData, lectures, sheets] = await Promise.all([
    getPopularYoutubeContents('PERFORMANCE'),
    getPopularYoutubeContents('LECTURE'),
    getPopularNextclassContents(),
    getPopularFullscoreContents(),
  ]);

  return (
    <div className="px-6 py-10">
      {/* 탭 버튼 */}
      <h1 className="text-2xl font-bold mb-4">{t('home.pupularVideo')}</h1>
      <PopularYoutubeTab />
      <div id="performance-tab">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {performanceData.map((item) => (
            <Link href={`/video/${item.id}`} key={item.id} className="block">
              <div className="rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 min-h-[320px]">
                <Image
                  src={item.thumbnailUrl}
                  alt={item.contentName}
                  width={400}
                  height={225}
                  className="w-full h-[225px] object-cover"
                />
                <div className="p-4">
                  <div className="text-base font-semibold leading-snug line-clamp-2 min-h-[44px]">
                    {item.contentName}
                  </div>
                  <p className="text-sm text-gray-500 mt-2 flex items-center gap-2">
                    <Image src="/like.png" alt="좋아요" width={15} height={15} />{item.likeCount.toLocaleString()}
                    <Image src="/eye.png" alt="조회수" width={16} height={16} />{item.viewCount.toLocaleString()}
                  </p>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
      <div id="lesson-tab" className="hidden">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {lessonData.map((item) => (
            <Link href={`/contents/${item.id}`} key={item.id} className="block">
              <div className="rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 min-h-[320px]">
                <Image
                  src={item.thumbnailUrl}
                  alt={item.contentName}
                  width={400}
                  height={225}
                  className="w-full h-[225px] object-cover"
                />
                <div className="p-4">
                  <div className="text-base font-semibold leading-snug line-clamp-2 min-h-[44px]">
                    {item.contentName}
                  </div>
                  <p className="text-sm text-gray-500 mt-2 flex items-center gap-2">
                    <Image src="/like.png" alt="좋아요" width={15} height={15} />{item.likeCount.toLocaleString()}
                    <Image src="/eye.png" alt="조회수" width={16} height={16} />{item.viewCount.toLocaleString()}
                  </p>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>

      {/* 인기 강의 콘텐츠 */}
      <div className="mt-16">
        <h1 className="text-2xl font-bold mb-4">{t('home.pupularLecture')}</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {lectures.map((item) => (
            <Link href={`/contents/${item.id}`} key={item.id} className="block">
              <div className="rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 min-h-[320px]">
                <Image
                  src={item.thumbnailUrl}
                  alt={item.contentName}
                  width={400}
                  height={225}
                  className="w-full h-[225px] object-cover"
                />
                <div className="p-4">
                  <div className="text-base font-semibold leading-snug line-clamp-2 min-h-[44px]">
                    {item.contentName}
                  </div>
                  <p className="text-sm text-gray-500 mt-2 flex items-center gap-2">
                    <Image src="/star.png" alt="평점" width={14} height={14} />
                    {(item.rating / 2).toLocaleString()}
                    <Image src="/person.png" alt="주문수" width={16} height={16} />
                    {item.orderCount.toLocaleString()} 
                  </p>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>

      {/* 인기 악보 콘텐츠 */}
      <div className="mt-16">
        <h1 className="text-2xl font-bold mb-4">{t('home.pupularSheet')}</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {sheets.map((item) => (
            <Link href={`/contents/${item.id}`} key={item.id} className="block">
              <div className="rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 min-h-[320px]">
                <Image
                  src={item.thumbnailUrl}
                  alt={item.contentName}
                  width={400}
                  height={225}
                  className="w-full h-[225px] object-cover"
                />
                <div className="p-4">
                  <div className="text-base font-semibold leading-snug line-clamp-2 min-h-[44px]">
                    {item.contentName}
                  </div>
                  <p className="text-sm text-gray-500 mt-2 flex items-center gap-2">
                    <Image src="/eye.png" alt="조회수" width={16} height={16} />
                    {item.viewCount.toLocaleString()}
                  </p>
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}