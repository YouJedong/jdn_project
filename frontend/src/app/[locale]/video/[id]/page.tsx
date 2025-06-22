import { getYoutubeContentDetail } from '@/lib/api/content'
import { getTranslations } from 'next-intl/server';
import Image from 'next/image';

export default async function VideoDetailPage(props: any) {
    const { params } = props;
    const { id } = params;
    const t = await getTranslations();

    const contentInfo = await getYoutubeContentDetail(id);
    
    return (
        <div className='px-6 lg:px-40 py-10'>
            <div className='flex flex-col lg:flex-row justify-between gap-6'>
                <div className="relative w-full lg:w-[1080px] h-[300px] lg:h-[300px]">
                    <Image
                        src={contentInfo.thumbnailUrl}
                        alt="영상 썸네일"
                        fill
                        className="object-cover rounded-2xl border"
                    />
                </div>
                <div className='py-2 pl-0 lg:pl-10 w-full '>
                    <div>
                      <div className='font-bold text-xl'>{contentInfo.contentName}</div>

                      <div className="flex items-center gap-1 mt-1">
                          <Image src="/eye.png" alt="조회수" width={16} height={16} />
                          <span>{contentInfo.viewCount}</span>
                      </div>

                      <div className="flex items-center gap-1 mt-1">
                          <Image src="/like.png" alt="좋아요" width={15} height={15} />
                          <span>{contentInfo.likeCount}</span>
                      </div>

                      <div className="flex items-center gap-1 mt-1">
                          <Image src="/comment.png" alt="댓글" width={15} height={15} />
                          <span>{contentInfo.commentCount}</span>
                      </div>

                      <div
                          className={`inline-block px-2 py-1 text-sm font-medium rounded-xl mt-1
                              ${contentInfo.videoType === 'PERFORMANCE'
                              ? 'bg-blue-100 text-blue-700'
                              : 'bg-green-100 text-green-700'}
                          `}
                      >
                        {contentInfo.videoType === 'PERFORMANCE' ? t('home.tabPerformance') : t('home.tabLesson')}
                      </div>
                    </div>

                    <a
                      href={`https://www.youtube.com/watch?v=${contentInfo.videoId}`}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="inline-block mt-4 px-4 py-2 bg-red-600 text-white text-sm font-semibold rounded hover:bg-red-700 self-start"
                    >
                      {t('video.detail.watchOnYoutube')}
                    </a>
                </div>
            </div>
            <div className='font-bold text-2xl mt-5 mb-2'>{t('video.detail.desc')}</div>
            <div className="border border-gray-300 rounded-lg px-4 lg:px-5 py-6 lg:py-8">
              <div className="text-sm whitespace-pre-line break-words">{contentInfo.description}</div>
            </div>
        </div>
    );
}