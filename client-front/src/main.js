import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import './assets/css/global.css'
import ElementPlus from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import ko from 'element-plus/es/locale/lang/ko'
import 'element-plus/dist/index.css'
import axios from 'axios'
import mitt from 'mitt';

import comUtil from "@/utils/ComUtil";

loadFonts()

axios.defaults.baseURL = 'http://localhost:8080'
const app = createApp(App);
app.use(router);
app.use(vuetify);
app.use(ElementPlus, { zIndex: 3000, locale: ko});
app.mount('#app');
const emitter = mitt();
app.config.globalProperties.$store = store;
app.config.globalProperties.$router = router;
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$eventBus = emitter;
app.config.globalProperties.$messageBox = ElMessageBox;
app.config.globalProperties.$message = ElMessage;
app.config.globalProperties.$comUtil = comUtil;

router.beforeEach(function (to, from, next){
    if(to.matched.some((routeInfo) => routeInfo.meta.authRequired == false)){
        next();
    }else if (!to.matched.some((routeInfo) => routeInfo.meta.authRequired == false)
              && store.state.remainTime > 0 && ((to.path != "/login" && to.path != "/" && from.name != null) || performance.navigation.TYPE_RELOAD)){
                next();
    } else {
        router.push({ name: "login", params: { toPath: to.fullPath }});
    }
});