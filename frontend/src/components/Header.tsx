'use client';

import Link from 'next/link';
import { useRouter, usePathname } from 'next/navigation';
import {useTranslations, useLocale} from 'next-intl';

export default function Header() {
  const locale = useLocale();
  const pathname = usePathname().replace(`/${locale}`, '') || '/';
  const t = useTranslations('header');

  const router = useRouter();
  const allPathName = usePathname();

  const navItems = [
    { href: '/', label: t('nav.home') },
    { href: '/video', label: t('nav.video') },
    { href: '/lectures', label: t('nav.lectures') },
    { href: '/scores', label: t('nav.scores') },
    { href: '/donate', label: t('nav.donate') }
  ];

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newLocale = e.target.value;

    // 현재 경로에서 locale을 교체
    const segments = allPathName.split('/');
    segments[1] = newLocale; // /[locale]/... 구조일 때
    const newPath = segments.join('/');

    router.push(newPath);
  };

  return (
    <>
      <header className="relative bg-white h-40 border-b border-gray-200">
        <div className="absolute top-4 right-4">
          <select onChange={handleChange} className="border rounded px-2 py-1 text-sm" defaultValue={locale}>
            <option value="ko">🇰🇷 {t('lang.ko')}</option>
            <option value="en">🇺🇸 {t('lang.en')}</option>
          </select>
        </div>
        <div className="h-full flex items-center justify-center">
          <Link href="/" className="text-6xl font-bold">
            JDnights
          </Link> 
        </div>
      </header>
      <nav className="h-12 flex justify-center items-center gap-14 border-b border-gray-200">
        {navItems.map(({ href, label }) => {
          const isActive = pathname === href;

          return (
            <Link
              key={href}
              href={href}
              className={isActive ? 'font-bold' : ''}
            >
              {label}
            </Link>
          );
        })}
      </nav>
    </>
    
  );
}