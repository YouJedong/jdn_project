import { MetadataRoute } from 'next'

type ContentItem = {
    id: number | string
    updatedAt?: string
}

const site = 'https://www.jdnights.com'
const locales = ['ko', 'en'] as const

// TODOJD 나중에 nc, fs도 추가 필요 
async function fetchAll(): Promise<ContentItem[]> {
    const url = `${process.env.NEXT_PUBLIC_API_URL}/api/content/yt?page=0&size=1000`
    const res = await fetch(url, { next: { revalidate: 3600 } })
    if (!res.ok) return []
    const result = await res.json()
    return Array.isArray(result?.data?.content) ? result.data.content : Array.isArray(result.data) ? result.data : []
}

export default async function sitemap(): Promise<MetadataRoute.Sitemap> {
    const now = new Date()

    const staticEntries: MetadataRoute.Sitemap = locales.flatMap((locale) => [
        {
            url: `${site}/${locale}`,
            lastModified: now,
            changeFrequency: 'daily',
            priority: 1.0,
            alternates: {
                languages: Object.fromEntries(locales.map((l) => [l, `${site}/${l}`])),
            },
        },
        {
            url: `${site}/${locale}/video`,
            lastModified: now,
            changeFrequency: 'weekly',
            priority: 0.8,
            alternates: {
                languages: Object.fromEntries(locales.map((l) => [l, `${site}/${l}`])),
            },
        },
        // TODOJD 페이지 추가되면 추가하기
    ])

    const items = await fetchAll()
    
    const dynamicEntries: MetadataRoute.Sitemap = items.flatMap((it) =>
        locales.map((locale) => ({
        url: `${site}/${locale}/video/${it.id}`,
        lastModified: it.updatedAt ? new Date(it.updatedAt) : now,
        changeFrequency: 'weekly',
        priority: 0.8,
        alternates: {
            languages: Object.fromEntries(
                locales.map((l) => [l, `${site}/${l}/video/${it.id}`])
            ),
        },
        }))
    )

    return [...staticEntries, ...dynamicEntries]
}