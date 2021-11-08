module.exports = {
  transpileDependencies: ["vuetify"],
  pwa: {
    workboxOptions: {
      // 프리캐시(pre-cache)할 파일 지정
      include: [/^index\.html$/, /\.css$/, /\.js$/, /^manifest\.json$/, /\.svg$/],
      // exclude는 반드시 기입해야 정상적으로 동작함.
      exclude: [],
      runtimeCaching: [
        {
          urlPattern: /\.jpg$/,
          handler: "cacheFirst",
          options: {
            cacheName: "jpg-cache",
            expiration: {
              maxEntries: 5, // 총 파일 5개까지 캐시
              maxAgeSeconds: 60 * 60 * 24, // 1일 캐시
            },
          },
        },
        {
          urlPattern: /\.png$/,
          handler: "cacheFirst",
          options: {
            cacheName: "png-cache",
            expiration: {
              maxEntries: 5, // 총 파일 5개까지 캐시
              maxAgeSeconds: 60 * 60 * 24, // 1일 캐시
            },
          },
        },
      ],
    },
  },
};
