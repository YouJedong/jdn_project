import '@/app/globals.css';
import {ReactNode} from 'react';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import {getTranslations} from 'next-intl/server';
import {NextIntlClientProvider} from 'next-intl';
import getRequestConfig from '@/i18n/request'; 

type Props = {
  children: ReactNode;
  params: {
    locale: string;
  };
};

export default async function LocaleLayout({ children, params }: Props) {
  const {locale} = await params;
  const t = await getTranslations();
  const title = t('meta.title');
  const description = t('meta.description');

  const {messages} = await getRequestConfig({
    requestLocale: Promise.resolve(locale)
  });

  return (
    <html lang='ko'>
      <head>
        <title>{title}</title>
        <meta name="description" content={description} />
      </head> 
      <body>
        <NextIntlClientProvider locale={locale} messages={messages}>
          <Header />
          <main className=''>{children}</main>
          <Footer />
        </NextIntlClientProvider>
      </body>
    </html>
  );
}