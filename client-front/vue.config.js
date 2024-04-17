const { defineConfig } = require('@vue/cli-service')

const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')

module.exports = defineConfig({
  publicPath: '/',
  lintOnSave:false,
  transpileDependencies: true,
  // css: {
  //   loaderOptions: {
  //     css: {
  //       additionalData: `
  //         @import "@/assets/css/global.css";
  //       `
  //     }
  //   }
  // },
  devServer: {
    proxy: "http://localhost:8080"
    // proxy: "uri"
  },
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
    ],
  },
  chainWebpack: (config) => {
    // Node.js 모듈을 번들에 포함하도록 설정
    config.externals({
      electron: 'commonjs electron'
    });
  },

  pluginOptions: {
    vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		},
    electronBuilder: {
      nodeIntegration: true,
      builderOptions: {
        extraFiles: [
          {
            "from": "src/electron",
            "to": "resources/app/settings",
            "filter": [
              "*.png",
              "*.html",
              "*.js"
            ]
          },
          {
            "from": "src/views",
            "to": "resources/app/views",
            "filter": [
              "*.vue"
            ]
          }
        ],
        win: {
          icon: "src/electron/icia-pc.png"
        }
      }
    },
  }
})
