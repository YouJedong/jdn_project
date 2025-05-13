import createMiddleware from 'next-intl/middleware';
import { NextRequest, NextResponse } from 'next/server';
import {routing} from './i18n/routing';

const intlMiddleware = createMiddleware(routing);

export default function middleware(request: NextRequest) {
  // 먼저 next-intl 미들웨어 처리
  const response = intlMiddleware(request);

  // pathname 예: /ko/videos
  const pathname = request.nextUrl.pathname;
  const match = pathname.match(/^\/(ko|en|ja)(\/|$)/); // 네가 사용하는 locale 목록
  const langCode = match?.[1] ?? 'ko';

  // 헤더에 추가
  response.headers.set('X-Lang-Code', langCode);

  return response;
}

export const config = {
  matcher: ['/((?!api|_next|.*\\..*).*)']
};

/*

import createMiddleware from 'next-intl/middleware';
import { NextRequest, NextResponse } from 'next/server';
import { routing } from './i18n/routing';

const intlMiddleware = createMiddleware(routing);

export default function middleware(request: NextRequest) {
  // 먼저 next-intl 미들웨어 처리
  const response = intlMiddleware(request);

  // pathname 예: /ko/videos
  const pathname = request.nextUrl.pathname;
  const match = pathname.match(/^\/(ko|en|ja)(\/|$)/); // 네가 사용하는 locale 목록
  const langCode = match?.[1] ?? 'ko';

  // 헤더에 추가
  response.headers.set('X-Lang-Code', langCode);
  response.headers.set('x-invoke-path', pathname);

  return response;
}

export const config = {
  matcher: ['/((?!api|_next|.*\\..*).*)'],
};

*/