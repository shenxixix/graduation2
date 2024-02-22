import { defineConfig, loadEnv } from 'vite';
import path from 'path';
import createVitePlugins from './vite/plugins';

// https://vitejs.dev/config/
export default defineConfig(({ mode, command }) => {
  const env = loadEnv(mode, process.cwd())
  const { VITE_APP_ENV } = env
  return {
    base: '/',
    resolve: {
      alias: {
        // 设置路径
        '~': path.resolve(__dirname, './'),
        // 设置别名
        '@': path.resolve(__dirname, './src'),
        '@img': path.resolve(__dirname, 'src/assets/images'),
        '@css': path.resolve(__dirname, 'src/assets/styles'),
      },
      // https://cn.vitejs.dev/config/#resolve-extensions
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
    },
    plugins: [
      createVitePlugins(env, command === 'build'),
    ],
    // vite 相关配置
    server: {
      port: 81,
      host: '0.0.0.0',
      open: true,
      // proxy: 'http://192.168.18.116:30000'
      proxy: {
        '^/zoom': {
          target: 'http://192.168.18.116:30000',
          changeOrigin: true,
          // rewrite: (p) => p.replace(/^\/prod-api/, '')
        },
        '^/fileserver': {
          target: 'http://hwop.car-siem.com',
          changeOrigin: true,
          // rewrite: (p) => p.replace(/^\/prod-api/, '')
        }
      }
    },
    css: {
      preprocessorOptions: {
        scss: {
          // scss全局变量
          additionalData: `@use "@/assets/styles/variable.scss" as *;`,
        },
      },
      postcss: {
        plugins: [
          // tailwindcss配置
          require('tailwindcss'),
          require('autoprefixer'),
          {
            postcssPlugin: 'internal:charset-removal',
            AtRule: {
              charset: (atRule) => {
                if (atRule.name === 'charset') {
                  atRule.remove();
                }
              }
            }
          }
        ]
      }
    }
  }
})
