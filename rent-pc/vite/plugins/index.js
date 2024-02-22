import vue from '@vitejs/plugin-vue'

import createAutoImport from './auto-import'
import createAutoComponents from './auto-components'
import createCompression from './compression'
import createSetupExtend from './setup-extend'
import pkgConfig from 'vite-plugin-package-config'; // 依赖预加载
import optimizationPersist from 'vite-plugin-optimize-persist'; // 依赖预加载
import createSvgIcon from './svg-icon'; // 自定义svg图标

export default function createVitePlugins(viteEnv, isBuild = false) {
    const vitePlugins = [vue()]
    vitePlugins.push(createAutoImport())
	vitePlugins.push(createSetupExtend())
    vitePlugins.push(createAutoComponents())
    vitePlugins.push(pkgConfig());
    vitePlugins.push(optimizationPersist());
    vitePlugins.push(createSvgIcon(isBuild))
	isBuild && vitePlugins.push(...createCompression(viteEnv))
    return vitePlugins
}
