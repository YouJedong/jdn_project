import { getRequestConfig } from "next-intl/server";
import { routing } from "./routing";
 
export default getRequestConfig(async ({ requestLocale }) => {
  let locale = await requestLocale;
 
  if (!locale || !routing.locales.includes(locale as never)) {
    locale = routing.defaultLocale;
  }
 
  return {
    locale,
    messages: (await import(`./messages/${locale}.json`)).default, // 상대 경로 확인, messages 폴더에 JSON 파일이 있어야 함
  };
});

