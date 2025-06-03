'use client'

type Pros = {
  defaultValue: string;
}

export default function SortSelector({defaultValue}:Pros) {
  return (
    <select 
      name='orderType' 
      defaultValue={defaultValue} 
      onChange={e => e.currentTarget.form?.submit()}
    >
      <option value="popular">인기순</option>
      <option value="latest">최신순</option>
      <option value="oldest">오래된순</option>
    </select>
  )

}