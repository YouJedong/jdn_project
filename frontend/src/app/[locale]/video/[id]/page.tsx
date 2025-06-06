import { getYoutubeContentDetail } from '@/lib/api/content'
import Image from 'next/image';

interface Props {
    params: { id: string };

}

export default async function VideoDetailPage({ params }: Props) {
    const { id } = await params;

    const contentInfo = await getYoutubeContentDetail(id);
    
    return (
        <div className='px-40 py-10'>
            <div className='flex justify-between'>
                <div className="relative w-[1080px] h-[300px]">
                    <Image
                        src={contentInfo.thumbnailUrl}
                        alt="영상 썸네일"
                        fill
                        className="object-cover rounded-2xl border"
                    />
                </div>
                <div className='py-2 pl-10 w-full'>
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
                    {contentInfo.videoType === 'PERFORMANCE' ? '연주' : '강의'}
                    </div>
                </div>
            </div>
            <div className='font-bold text-2xl mt-5 mb-2'>설명</div>
            <div className='border border-gray-300 rounded-lg px-5 py-8'>
                <div className='whitespace-pre-line text-sm'>{contentInfo.description}</div>
            </div>
                

        </div>
    );
}