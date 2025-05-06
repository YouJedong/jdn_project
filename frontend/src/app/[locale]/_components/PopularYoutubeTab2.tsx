'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import Link from 'next/link';

export default function PopularYoutubeTab({
  initialTab,
  initialData,
  locale,
}: {
  initialTab: string;
  initialData: any[];
  locale: string;
}) {
  const [tab, setTab] = useState(initialTab);
  const [data, setData] = useState(initialData);
  const router = useRouter();

  const handleTabChange = async (newTab: 'PERFORMANCE' | 'LECTURE') => {
    if (newTab === tab) return;

    router.push(`/${locale}?tab=${newTab.toLowerCase()}`, { scroll: false });
    setTab(newTab);

    const res = await fetch(`/api/content/yt/popular/${newTab}`);
    const json = await res.json();
    setData(json.data);
  };

  return (
    <>
      <div className="flex gap-4 mb-6">
        <button
          onClick={() => handleTabChange('PERFORMANCE')}
          className={`px-4 py-2 rounded-full border ${tab === 'PERFORMANCE' ? 'bg-black text-white' : 'bg-white text-black'}`}
        >
          연주
        </button>
        <button
          onClick={() => handleTabChange('LECTURE')}
          className={`px-4 py-2 rounded-full border ${tab === 'LECTURE' ? 'bg-black text-white' : 'bg-white text-black'}`}
        >
          강의
        </button>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {data.map((item) => (
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
    </>
  );
}