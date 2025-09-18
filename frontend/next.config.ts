import dotenv from 'dotenv';
import path from 'path';

const envFile = process.env.ENV_FILE || '.env.local';
dotenv.config({ 
  path: path.resolve(__dirname, envFile), 
  override: true 
});

const createNextIntlPlugin = require('next-intl/plugin');

const withNextIntl = createNextIntlPlugin();

/** @type {import('next').NextConfig} */
const nextConfig = withNextIntl({
  eslint: {
    ignoreDuringBuilds: true,
  },
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: `${process.env.NEXT_PUBLIC_API_URL}/api/:path*`,
      },
    ];
  },
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'i.ytimg.com',
        pathname: '/vi/**',
      },
      {
        //  https://next-class.s3.ap-northeast-2.amazonaws.com/production/images/20231003/140231191.png
        protocol: 'https',
        hostname: 'next-class.s3.ap-northeast-2.amazonaws.com',
        pathname: '/production/images/**',
      },
      {
        // https://www.fullscore.co.kr/data/prdimg/2203310008_R.jpg
        protocol: 'https',
        hostname: 'www.fullscore.co.kr',
        pathname: '/data/**'
      }
    ],
  },
});

module.exports = nextConfig