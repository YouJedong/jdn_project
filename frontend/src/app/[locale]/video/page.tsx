import { getTranslations } from 'next-intl/server';
import { getYoutubeContents } from '@/lib/api/content'
import Link from 'next/link';
import Image from 'next/image';

interface Props {
  searchParams: {
    page?: string;
    size?: string;
  }
  
}


export default async function VideoListPage({ searchParams } : Props) {
  const t = await getTranslations();
  const page = Number(searchParams.page ?? 0);
  const { content: youtubeContents, pageNumber, totalPages } = await getYoutubeContents(searchParams);

  return (
    <div className="px-6 py-10">
      <h1 className="text-2xl font-bold mb-4">{t('video.list.title')}</h1>
      <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6'>
        {youtubeContents.map((item) => (
          <Link href={`/content/${item.id}`} key={item.id} className="block">
            <div className='rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 min-h[320px]'>
              <Image
                src={item.thumbnailUrl}
                alt={item.contentName}
                width={400}
                height={225}
                className='w-full h-[255px] object-cover'
              />
              <div className='p-4'>
                <div className='text-base font-semibold leading-snug line-clamp-2 min-h-[44px]'>
                  {item.contentName}
                </div>
                <p className='text-sm text-gray-500 mt-2 flex items-center gap-2'>
                  <Image src="/like.png" alt="좋아요" width={15} height={15} />{item.likeCount.toLocaleString()}
                  <Image src="/eye.png" alt="조회수" width={16} height={16} />{item.viewCount.toLocaleString()}
                </p>
              </div>              
            </div>
          </Link>
        ))}
      </div>
      <div className="flex justify-center mt-8 gap-2">
        {/* 맨 처음 페이지로 */}
        {page > 0 && (
          <>
            <Link href="?page=0" className="px-3 py-1 border rounded">
              «
            </Link>
          </>
        )}

        {/* 번호 목록 */}
        {Array.from({ length: totalPages }, (_, i) => i)
          .slice(
            Math.max(0, Math.min(page - 2, totalPages - 5)),
            Math.min(totalPages, Math.max(5, page + 3))
          )
          .map(i => (
            <Link
              key={i}
              href={`?page=${i}`}
              className={`px-3 py-1 border rounded ${i === page ? 'bg-black text-white' : ''}`}
            >
              {i + 1}
            </Link>
          ))}

        {/* 다음, 맨 끝 페이지로 */}
        {page < totalPages - 1 && (
          <>
            <Link href={`?page=${totalPages - 1}`} className="px-3 py-1 border rounded">
              »
            </Link>
          </>
        )}
      </div>
    </div>
  )
}