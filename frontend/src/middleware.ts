import createMiddleware from 'next-intl/middleware';
import { NextRequest } from 'next/server';
import {routing} from './i18n/routing';

const intlMiddleware = createMiddleware(routing);

export default function middleware(request: NextRequest) {
  // 먼저 next-intl 미들웨어 처리
  const response = intlMiddleware(request);

  // next-intl이 감지한 locale을 재활용
  const locale = request.nextUrl.pathname.split('/')[1] || routing.defaultLocale;
  response.headers.set('X-Lang-Code', locale);

  return response;
}

export const config = {
  matcher: ['/((?!api|_next|.*\\..*).*)']
};
