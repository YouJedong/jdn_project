import { getYoutubeContentDetail } from '@/lib/api/content'

interface Props {
    params: { id: string };

}

export default async function VideoDetailPage({ params }: Props) {
    const contentId = await params.id;

    const contentInfo = await getYoutubeContentDetail(contentId);
    
}