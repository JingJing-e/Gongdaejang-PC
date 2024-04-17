<template>
    <div class="home-header">
        <el-button
            type="primary"
            @click="fnc.openPopup('PopOrder')"
        >주문하기
        </el-button>
        <el-button
            type="primary"
            @click="fnc.openPopup('PopCharge')"
        >요금제 충전하기
        </el-button>
        <el-button
            type="primary"
            @click="fnc.logout()"
        >사용종료
        </el-button>
    </div>
    <el-dialog v-model="state.PopGame.pop" :title="'게임창'">
        <PopGames 
            v-if="state.PopGame.pop == true"
        />
    </el-dialog>
    <el-dialog v-model="state.PopOrder.pop" :title="'주문창'" :close-on-click-modal="false">
        <PopOrders 
            v-if="state.PopOrder.pop == true"    
        />
    </el-dialog>
    <el-dialog v-model="state.PopMessage.pop" :title="'메세지창'">
        <PopMessages 
            v-if="state.PopMessage.pop == true"
        />
    </el-dialog>
    <el-dialog v-model="state.PopCharge.pop" :title="'요금제 충전창'">
        <PopCharges
            v-if="state.PopCharge.pop == true"
        />
    </el-dialog>
</template>

<script>
let ipcRenderer;
import { reactive, onMounted, onUnmounted, getCurrentInstance, ref } from 'vue';
import PopGames from './PopGames.vue'
import PopOrders from './PopOrders.vue'
import PopMessages from './PopOrders.vue'
import PopCharges from './PopCharge.vue'
import axios from 'axios';
if (window && window.process && window.process.type) {
  import('electron').then((electron) => {
    ipcRenderer = electron.ipcRenderer;
  })
};

export default {
    components : {
        PopGames,
        PopOrders,
        PopMessages,
        PopCharges,
    }, 
    setup(){
        const { proxy } = getCurrentInstance();
        const store = proxy.$store;
        const router = proxy.$router;
        const axios = proxy.$axios;
        const controllBox = ref(null);
        let isDragging = false;
        let offsetX, offsetY;
        
        const state = reactive({
            PopGame: {
                pop: false,
            },
            PopOrder: {
                pop: false,
            },
            PopMessage: {
                pop: false,
            },
            PopCharge: {
                pop: false,
            },
            PopControllBox: {
                pop: true,
            },
            userInfo : store.state.userInfo,
        })

        let logoutTimer = null;

        if (window && window.process && window.process.type) {

        }
        onMounted(() => {
            logoutTimer = setInterval(fnc.keepLogin, 1000);
            controllBox.value = document.querySelector('.controll-box-area');
        });

        onUnmounted(() => {
            clearInterval(logoutTimer);
        });

        const fnc = {
            goPage(item) {
                router.push({"path" : item});
            },
            openPopup(item) {
                switch(item){
                    case "PopGame" :
                        // state.PopGame.pop = true;
                        break;
                    case "PopOrder" :
                        // state.PopOrder.pop = true;
                        ipcRenderer.send("createOrderWin");
                        break;
                    case "PopMessage" :
                        // state.PopMessage.pop = true;
                        break;
                    case "PopCharge" :
                        ipcRenderer.send("createChargeWin");
                        break;
                }
            },
            logout(){
                axios.put(`/member`, {
                    id : state.userInfo.id,
                    remainTime : state.userInfo.remainTime
                }).then((res) => {
                    axios.put(`/log`, {
                        memberId : state.userInfo.id
                    }).then((res) => {
                        if (window && window.process && window.process.type) {
                            ipcRenderer.send('logout');
                        }else{
                            router.push({"path" : "/login"});
                        }
                        localStorage.clear();
                    })
                }) 
            },
        }

        return {
            state
            , fnc
        }
    }
}
</script>