import autoImport from 'unplugin-auto-import/vite';
import { ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// 自动引入composition api 
export default function createAutoImport() {
    return autoImport({
        imports: [
            'vue',
            'vue-router',
            'pinia', // 状态管理器
        ],
        resolvers: [
            // 自动引入ElementPlus函数
            ElementPlusResolver({
                importStyle: false, // 是否需要随引入加载对应的组件样式
            }),
        ],
        dts: false
    })
}
