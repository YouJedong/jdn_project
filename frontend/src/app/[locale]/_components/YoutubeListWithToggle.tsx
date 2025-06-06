'use client'

import { YoutubeContent } from "@/types/content";
import Image from 'next/image';
import Link from "next/link";
import { useState } from "react";

export default function YoutubeListWithToggle({ youtubeContents }: { youtubeContents: YoutubeContent[] }) {
    const [viewType, setViewType] = useState<'card' | 'list'>('card');

    return (
        <>
            <div className="flex justify-end mb-4">
                <button
                    onClick={() => setViewType('card')}
                    className={`${viewType === 'card' ? 'bg-gray-200' : ''} px-2 py-1 border border-r-0 rounded-l-md`}
                >
                    <Image src='/4square.png' alt="카드 목록 보기" width={20} height={20}/> 
                </button>
                <button
                    onClick={() => setViewType('list')}
                    className={`${viewType === 'list' ? 'bg-gray-200' : ''} px-2 py-1 border rounded-r-md`}
                >
                    <Image src='/burger.png' alt="글 목록 보기" width={20} height={20}/> 
                </button>
            </div>

            <div className={viewType === 'card' ? 'grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6' : 'flex flex-col gap-4'}>
                {youtubeContents.map((item) => (
                <Link href={`/video/${item.id}`} key={item.id} className="block">
                    {viewType === 'card' ? (
                    <div className='rounded-xl overflow-hidden shadow-md hover:shadow-lg transition-shadow duration-300 min-h-[320px]'>
                        <Image src={item.thumbnailUrl} alt={item.contentName} width={400} height={225} className='w-full h-[255px] object-cover' />
                        <div className='p-4'>
                        <div className='text-base font-semibold leading-snug line-clamp-2 min-h-[44px]'>{item.contentName}</div>
                        <p className='text-sm text-gray-500 mt-2 flex items-center gap-2'>
                            <Image src="/like.png" alt="좋아요" width={15} height={15} />{item.likeCount.toLocaleString()}
                            <Image src="/eye.png" alt="조회수" width={16} height={16} />{item.viewCount.toLocaleString()}
                        </p>
                        </div>
                    </div>
                    ) : (
                    <div className="flex items-start gap-4 p-4 border rounded-lg hover:bg-gray-50 transition">
                        <Image src={item.thumbnailUrl} alt={item.contentName} width={160} height={90} className='rounded-lg object-cover' />
                        <div className="flex flex-col justify-between">
                        <div className='text-base font-semibold leading-snug line-clamp-2'>{item.contentName}</div>
                        <p className='text-sm text-gray-500 mt-2 flex items-center gap-2'>
                            <Image src="/like.png" alt="좋아요" width={15} height={15} />{item.likeCount.toLocaleString()}
                            <Image src="/eye.png" alt="조회수" width={16} height={16} />{item.viewCount.toLocaleString()}
                        </p>
                        </div>
                    </div>
                    )}
                </Link>
                ))}
            </div>
        </>
    );

}