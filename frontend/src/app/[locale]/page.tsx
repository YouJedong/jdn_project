import {getTranslations} from 'next-intl/server';
import {setRequestLocale} from 'next-intl/server';
import {routing} from '@/i18n/routing';

type Props = {
  params: {
    locale: string;
  };
};

export async function generateStaticParams() {
  return routing.locales.map((locale) => ({locale}));
}

export default async function Page({params: {locale}}: Props) {
  const t = await getTranslations({locale});
  return <h1>{t('home.title')}</h1>;
}