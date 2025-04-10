import {ReactNode} from 'react';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import {setRequestLocale, getTranslations} from 'next-intl/server';

type Props = {
  children: ReactNode;
  params: {
    locale: string;
  };
};

export default async function LocaleLayout({children, params: {locale}}: Props) {
  setRequestLocale(locale);

  const t = await getTranslations({locale});
  const title = t('meta.title');
  const description = t('meta.description');

  return (
    <html lang={locale}>
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