'use client';

import {Link, useRouter, usePathname} from '@/i18n/navigation';
import {useTranslations, useLocale} from 'next-intl';

export default function Header() {
  const locale = useLocale();
  const pathname = usePathname();
  const t = useTranslations('header');
  const router = useRouter();

  const navItems = [
    { href: '/', label: t('nav.home') },
    { href: '/video', label: t('nav.video') },
    { href: '/lectures', label: t('nav.lectures') }, // /not-found
    { href: '/scores', label: t('nav.scores') },
    { href: '/donate', label: t('nav.donate') }
  ];

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newLocale = e.target.value;
    router.replace(pathname, {locale: newLocale});
  };

  return (
    <>
      <header className="relative bg-white h-40 border-b border-gray-200">
        <div className="absolute top-4 right-4">
          <select 
            onChange={handleChange} 
            className="border rounded px-2 py-1 text-sm" 
            defaultValue={locale}
          >
            <option value="ko">🇰🇷 {t('lang.ko')}</option>
            <option value="en">🇺🇸 {t('lang.en')}</option>
          </select>
        </div>
        <div className="h-full flex flex-col items-center justify-center">
          <div>
            <Link href="/" className="text-6xl font-bold">
              JDnights.Beta
            </Link> 
          </div>
          <div className=''>This site is currently in beta.</div>
        </div>
      </header>
      <nav className="h-12 flex justify-center items-center gap-14 border-b border-gray-200">
        {navItems.map(({ href, label }) => {
          const isActive = pathname === href;

          return (
            <Link
              key={href}
              href={['/lectures', '/scores', '/donate'].includes(href) ? '/not-found' : href}
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