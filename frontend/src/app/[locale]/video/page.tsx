export const revalidate = 43200;
import { getTranslations } from 'next-intl/server';
import { getYoutubeContents } from '@/lib/api/content'
import Link from 'next/link';
import Image from 'next/image';

import SortSelector from '../_components/SortSelector';
import YoutubeListWithToggle from '../_components/YoutubeListWithToggle';

export default async function VideoListPage(props: any) {
  const t = await getTranslations();

  const { searchParams } = props;
  const page = Number(searchParams.page ?? 0);
  const keyword = searchParams.keyword
  const orderType = searchParams.orderType ?? 'popular';

  const { content: youtubeContents, totalPages } = await getYoutubeContents(searchParams);

  return (
    <div className="px-6 py-10">
      <h1 className="text-2xl font-bold mb-4">{t('video.list.title')}</h1>

      <div >
        <form action="/video" method="GET">
          <div className='flex justify-between item-center w-full mb-5'>
            <div className='flex item-center border rounded-lg border-gray-300 h-12 p-2 w-100px'>
              <Image src="/magnifier.png" alt='찾기' width={28} height={28} />
              <input 
                type="text"
                name="keyword"
                defaultValue={keyword ?? ''}
                placeholder={t('video.list.keyword')}
                className='ml-2 w-full focus:outline-none'
              />
            </div>
            <div className='flex'>
              <SortSelector defaultValue={orderType} />
            </div>
          </div>
        </form>
      </div>

      <YoutubeListWithToggle youtubeContents={youtubeContents} />

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