'use client'

import { useEffect, useState } from 'react'

export default function Home() {
  const [message, setMessage] = useState('')

  useEffect(() => {
    fetch('/api/hello') // 실제 호출은 http://localhost:8080/api/hello 로 프록시됨
      .then((res) => res.text())
      .then((text) => setMessage(text))
  }, [])

  return <main>백엔드 응답: {message}</main>
}