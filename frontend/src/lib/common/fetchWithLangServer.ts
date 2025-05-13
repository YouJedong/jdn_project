import { headers } from 'next/headers'; 

export const fetchWithLangServer = async (
  input: RequestInfo | URL,
  init: RequestInit = {}
): Promise<Response> => {
  const h = await headers();
  const langCode = h.get('X-Lang-Code') || 'ko';

  return fetch(input, {
    ...init,
    headers: {
      ...init.headers,
      'X-Lang-Code': langCode,
    },
  });
};