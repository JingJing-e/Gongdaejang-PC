<template>
    <!-- v-show="state.PopControllBox.pop" -->
    <div 
        class="controll-box-area"
        ref="dragControllBox"
        @mousedown="fnc.startDrag"
        @mousemove="fnc.drag"
        @mouseup="fnc.stopDrag"
        @mouseleave="fnc.stopDrag"
        >
        <div class="header">
            <div>
                ICIA 
                <span>프리미엄 공대장피씨</span>
            </div>
            <div>
                <el-button
                    @click="state.PopControllBox.pop = false"
                >X</el-button>
            </div>
        </div>
        <div class="section1">
            <div>
                no.
                <span>{{ state.pcNum.padStart(2, '0') }}</span>
            </div>
            <div>
                <el-button>
                    자리이동
                </el-button>
                <el-button
                    @click="fnc.logout()"
                >사용종료</el-button>
            </div>
        </div>
        <div class="section2">
            <div>
                ID
                <span>{{ state.userInfo.loginId }}</span>
            </div>
            <div>
                <span><span>회원정보</span></span>
                <span>ICIA앱 연동</span>
            </div>
        </div>
        <div class="section3">
            <div>
                <div>제휴혜택<br/>모아보기</div>
            </div>
            <div>
                <div>사용요금</div>
                <div>0</div>
            </div>
            <div>
                <div>사용시간</div>
                <div>
                    {{ String(Math.floor(state.usedTime/3600)).padStart(2, '0') }}
                    : {{ String(Math.floor(state.usedTime/60) - Math.floor(state.usedTime/3600)*60).padStart(2, '0') }}
                </div>
            </div>
            <div>
                <div>남은시간</div>
                <div>
                    {{ String(Math.floor(state.userInfo.remainTime/3600)).padStart(2, '0') }}
                    : {{ String(Math.floor(state.userInfo.remainTime/60) - Math.floor(state.userInfo.remainTime/3600)*60).padStart(2, '0') }}
                </div>
            </div>
        </div>
        <div class="section4">
            <el-button
                type="primary"
                @click="fnc.openPopup('PopOrder')"
            >
            먹거리 주문
            </el-button>
            <el-button
                type="primary"
                @click="fnc.openPopup('PopCharge')"
            >
            요금제 구매
            </el-button>
            <el-button
                type="primary"
            >
            쿠폰함
            </el-button>
            <el-button
                type="primary"
            >
            쿠폰 사용
            </el-button>
            <el-button
                type="primary"
            >
            ICIA포인트
            </el-button>
        </div>
        <div class="section5">
            <div>
                선불연장
            </div>
            <div>
                호출
            </div>
            <div>
                메세지
            </div>
            <div>
                요금정보
            </div>
        </div>
        <div class="section6">
            <div>
                <div></div>
                <div>아키에이지</div>
                <div></div>
                <div>FC온라인</div>
                <div></div>
                <div>한게임포커</div>
                <div></div>
                <div>서든어택</div>
                <div></div>
                <div>어둠의전설</div>
                <div></div>
                <div>VX</div>
            </div>
            <div></div>
        </div>
    </div>
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
            pcNum : store.state.pcNum,
            usedTime : store.state.usedTime,
        })

        let logoutTimer = null;
        if (window && window.process && window.process.type) {
            
            ipcRenderer.on('navigate-to-order', () => {
                router.push({"path" : "/order"});
            });

            ipcRenderer.on('navigate-to-charge', () => {
                router.push({"path" : "/charge"});
            });

            ipcRenderer.on('navigate-to-header', () => {
                router.push({"path" : "/header"});
            });
            
        }
        onMounted(() => {
            if (window && window.process && window.process.type) {
                ipcRenderer.send('checkMainWin');
                // ipcRenderer.send('createMenuWin');
            }
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
            keepLogin(){
                if(state.PopControllBox.pop === false){
                    state.PopControllBox.pop = true;
                    controllBox.value = document.querySelector('.controll-box-area');
                }
                state.userInfo.remainTime -= 1;
                store.commit("setUserInfo", state.userInfo);
                state.usedTime += 1;
                store.commit("setUsedTime", state.usedTime);
                if(state.userInfo.remainTime <= 0){
                    fnc.logout();
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