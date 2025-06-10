'use client'

import {useTranslations} from 'next-intl';

type Pros = {
  defaultValue: string;
}

export default function SortSelector({defaultValue}:Pros) {
  const t = useTranslations();

  return (
    <select 
      name='orderType' 
      defaultValue={defaultValue} 
      onChange={e => e.currentTarget.form?.submit()}
    >
      <option value="popular">{t('video.list.sort.popular')}</option>
      <option value="latest">{t('video.list.sort.new')}</option>
      <option value="oldest">{t('video.list.sort.old')}</option>
    </select>
  )

}