import {getTranslations} from 'next-intl/server';
import {routing} from '@/i18n/routing';
import { getHomeContent } from '@/lib/api/content';

export async function generateStaticParams() {
  return routing.locales.map((locale) => ({locale}));
}

export default async function Page() {
  const t = await getTranslations();
  const data = await getHomeContent();
  

  return <>
    <h1>{t('home.title')}</h1>
  </>;
}