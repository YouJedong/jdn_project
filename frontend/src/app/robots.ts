import { MetadataRoute } from 'next'

export default function robots(): MetadataRoute.Robots {
  const site = 'https://www.jdnights.com'

  return {
    rules: [
      {
        userAgent: '*', // 모든 크롤러 허용
        allow: '/',      // 사이트 전체 접근 허용
      },
    ],
    sitemap: `${site}/sitemap.xml`,
  }
}