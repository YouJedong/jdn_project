'use client';

import Link from 'next/link';
import {usePathname} from 'next/navigation';
import {useTranslations, useLocale} from 'next-intl';

export default function Header() {
  const locale = useLocale();
  const pathname = usePathname().replace(`/${locale}`, '') || '/';
  const t = useTranslations('header');

  const navItems = [
    { href: '/', label: t('nav.home') },
    { href: '/video', label: t('nav.video') },
    { href: '/lectures', label: t('nav.lectures') },
    { href: '/scores', label: t('nav.scores') },
    { href: '/donate', label: t('nav.donate') }
  ];

  return (
    <>
      <header className="relative bg-white h-40 border-b border-gray-200">
        <div className="absolute top-4 right-4">
          <select className="border rounded px-2 py-1 text-sm">
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
          console.log(pathname);
          console.log(locale);
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