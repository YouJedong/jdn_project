import '@/app/globals.css';
import Header from '@/components/Header';
import Footer from '@/components/Footer';
import {getTranslations} from 'next-intl/server';
import {NextIntlClientProvider} from 'next-intl';
import getRequestConfig from '@/i18n/request'; 
import { Metadata } from 'next';

export async function generateMetadata(props: any): Promise<Metadata> {
  const { locale } = props;
  const t = await getTranslations({ locale: locale })
  const title = t('meta.common.title')
  const description = t('meta.common.description')

  return {
    metadataBase: new URL('https://www.jdnights.com'),
    title: {
        default: 'JDnights',
        template: '%s | JDnights',
    },
    description,
    alternates: {
      languages: {
        ko: '/ko',
        en: '/en',
      },
    },
    openGraph: {
      type: 'website',
      url: `/${locale}`,
      siteName: 'JDnights',
      title,
      description,
      images: [{ url: '/og/default.png', width: 1200, height: 630, alt: 'JDnights' }], // TODOJD 추가하기
    },
    robots: {
        index: true,
        follow: true,
        googleBot: {
            index: true,
            follow: true,
            'max-image-preview': 'large',
        }
    },
    icons: {
        icon: '/favicon.ico', // TODOJD 이미지 추가하기
        apple: '/apple-touch-icon.png', // TODOJD 이미지 추가하기
    }
  }
}
export const viewport = {
  width: 'device-width',
  initialScale: 1,
  themeColor: '#ffffff'
}

export default async function LocaleLayout(props: any) {
  const { children, params } = props;
  const { locale } = await params;
  const { messages } = await getRequestConfig({ requestLocale: Promise.resolve(locale) })

  return (
    <html lang={locale}>
      <body>
        <NextIntlClientProvider locale={locale} messages={messages}>
          <Header />
          <main className="min-h-screen">{children}</main>
          <Footer />
        </NextIntlClientProvider>
      </body>
    </html>
  )
}
