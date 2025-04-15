import {ReactNode} from 'react';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import {getTranslations} from 'next-intl/server';

type Props = {
  children: ReactNode;
};

export default async function LocaleLayout({ children }: Props) {
  const t = await getTranslations();
  const title = t('meta.title');
  const description = t('meta.description');

  return (
    <html lang='ko'>
      <head>
        <title>{title}</title>
        <meta name="description" content={description} />
      </head>
      <body>
        <Header />
        <main>{children}</main>
        <Footer />
      </body>
    </html>
  );
}