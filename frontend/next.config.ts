/** @type {import('next').NextConfig} */
const createNextIntlPlugin = require('next-intl/plugin');

const withNextIntl = createNextIntlPlugin();

const nextConfig = withNextIntl({
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/api/:path*',
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
      }
    ],
  },
});

module.exports = nextConfig