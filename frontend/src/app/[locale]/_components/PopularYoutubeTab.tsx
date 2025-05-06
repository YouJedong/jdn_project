'use client';

import { useState } from 'react';

export default function PopularYoutubeTab() {
    const [activeTab, setActiveTab] = useState<'PERFORMANCE' | 'LECTURE'>('PERFORMANCE');

    const showTab = (tab: 'PERFORMANCE' | 'LECTURE') => {
      setActiveTab(tab);
      document.getElementById('performance-tab')?.classList.toggle('hidden', tab !== 'PERFORMANCE');
      document.getElementById('lesson-tab')?.classList.toggle('hidden', tab !== 'LECTURE');
    };
  

  return (
    <div className="flex gap-2 mb-4">
      <button
        onClick={() => showTab('PERFORMANCE')}
        className={`px-4 py-2 rounded-full border ${
          activeTab === 'PERFORMANCE' ? 'bg-black text-white' : 'bg-white text-black'
        }`}
      >
        연주
      </button>
      <button
        onClick={() => showTab('LECTURE')}
        className={`px-4 py-2 rounded-full border ${
          activeTab === 'LECTURE' ? 'bg-black text-white' : 'bg-white text-black'
        }`}
      >
        강의
      </button>
    </div>
  );
}