'use client';

import Link from 'next/link';

export default function Header() {
  return (
    <header className="bg-white shadow-md px-4 py-3">
      <div className="max-w-6xl mx-auto flex items-center justify-between">
        {/* 로고 or 사이트명 */}
        <Link href="/" className="text-xl font-bold">
          🎸 Jedong Music
        </Link>

        {/* PC 메뉴 */}
        <nav className="hidden md:flex gap-6 items-center text-sm">
          <Link href="/about">소개</Link>
          <Link href="/donate">후원</Link>
          <Link href="/lectures">강의</Link>
          <Link href="/scores">악보</Link>

          {/* 언어 전환 */}
          <select className="border rounded px-2 py-1 text-sm">
            <option value="ko">🇰🇷 한국어</option>
            <option value="en">🇺🇸 English</option>
          </select>
        </nav>

        {/* 모바일 메뉴 아이콘 */}
        <button className="md:hidden text-2xl">☰</button>
      </div>
    </header>
  );
}