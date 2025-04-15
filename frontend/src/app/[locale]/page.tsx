import {getTranslations} from 'next-intl/server';
import {routing} from '@/i18n/routing';

export async function generateStaticParams() {
  return routing.locales.map((locale) => ({locale}));
}

export default async function Page() {
  const t = await getTranslations();
  return <h1>{t('home.title')}</h1>;
}