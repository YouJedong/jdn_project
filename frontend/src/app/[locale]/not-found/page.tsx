import { getTranslations } from 'next-intl/server';

export default async function NotFound() {
  const t = await getTranslations();

  return (
    <div className="flex flex-col items-center h-screen mt-20">
      <h1 className="text-3xl font-bold">{t('not-found.desc')}</h1>
    </div>
  );

}