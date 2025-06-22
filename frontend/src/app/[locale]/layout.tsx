import '@/app/globals.css';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import {getTranslations} from 'next-intl/server';
import {NextIntlClientProvider} from 'next-intl';
import getRequestConfig from '@/i18n/request'; 

export default async function LocaleLayout(
  props: any 
) {
  const { children, params } = props;
  const locale = params.locale;
  const t = await getTranslations();
  const title = t('meta.title');
  const description = t('meta.description');

  const {messages} = await getRequestConfig({
    requestLocale: Promise.resolve(locale)
  });

  return (
    <html lang={locale}>
      <head>
        <title>{title}</title>
        <meta name="description" content={description} />
      </head> 
      <body>
        <NextIntlClientProvider locale={locale} messages={messages}>
          <Header />
          <main className='min-h-screen'>{children}</main>
          <Footer />
        </NextIntlClientProvider>
      </body>
    </html>
  );
}