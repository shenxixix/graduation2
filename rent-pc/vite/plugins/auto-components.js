import Components from 'unplugin-vue-components/vite' // 自动引入组件
import { ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// 自动引入组件
export default function createAutoComponents() {
    return Components({
        dts: false,
        dirs: ['src/components'], // 公共组件自动引入
        resolvers: [
            // 自动引入ElementPlus组件
            ElementPlusResolver({
                importStyle: false, // 是否需要随引入加载对应的组件样式
            }),
        ]
    })
}
