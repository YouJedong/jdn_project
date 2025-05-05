import { getTranslations } from 'next-intl/server';
import { routing } from '@/i18n/routing';
import { getPopularYoutubeContents } from '@/lib/api/content';
import PopularYoutubeTab from './components/PopularYoutubeTab';


export async function generateStaticParams() {
  return routing.locales.map((locale) => ({locale}));
}

export default async function Page({
  params,
  searchParams,
}: {
  params: { locale: string };
  searchParams: { tab?: string };
}) {
  const t = await getTranslations();
  const { locale } = await params
  const { tab } = await searchParams;
  const pTab = tab?.toUpperCase() === 'LECTURE' ? 'LECTURE' : 'PERFORMANCE';

  const data = await getPopularYoutubeContents(pTab);

  return (
    <div className="px-6 py-10">
      <h1 className="text-2xl font-bold mb-6">{t('home.pupularVideo')}</h1>
      <PopularYoutubeTab initialTab={pTab} initialData={data} locale={locale} />
    </div>
  )
}