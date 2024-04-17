<template>
    <div class="chargeOrder-container">
        <div class="charge-container">
            <div>요금제 구매</div>
            <div class="charge-wrapper">
                <div
                    v-for="(item, index) in state.feeCharge"
                    :key="index"
                    :class="[{'active': state.activeCharge === index }, 'charge-detail']"
                    @click="fnc.activateCharge(index); fnc.chargeInfo(item); fnc.payment(item)"
                >
                    <div>{{ item.name }}</div>
                    <div>
                        <div>
                            {{ String(Math.floor(item.time/3600)).padStart(2, '0') }}
                            : 
                            {{ String(Math.floor(item.time/60) - Math.floor(item.time/3600)*60).padStart(2, '0') }}
                        </div>
                        <div>{{ item.price }} 원</div>
                    </div>
                </div>
            </div>
            <div class="notice">
                ※ 좌석에서 로그인 이후 충전시에는 계정의 남은시간이 10분 이상일 때 충전이 가능합니다.
            </div>
        </div>
        <div class="info-wrapper">
            <div class="user-info">
                <div>
                    <div>잔여시간</div>
                    <div>
                        {{ String(Math.floor(state.userInfo.remainTime/3600)).padStart(2, '0') }}시간 
                        {{ String(Math.floor(state.userInfo.remainTime/60) - Math.floor(state.userInfo.remainTime/3600)*60).padStart(2, '0') }}분
                    </div>
                </div>
                <div>
                    <div>보유 포인트</div>
                    <div>0 P</div>
                </div>
            </div>
            <div class="qr-area">
                <div v-if="state.chargeOrder.price != undefined">
                    <div>{{ state.chargeOrder.name }}</div>
                    <div>{{ state.chargeOrder.price }}원({{ String(Math.floor(state.chargeOrder.time/3600)).padStart(2, '0') }}:{{ String(Math.floor(state.chargeOrder.time/60) - Math.floor(state.chargeOrder.time/3600)*60).padStart(2, '0') }})</div>
                    <div class="qr-wrapper">
                        <div class="qr-code">
                            <div><webview v-if="state.kakaoPayUrl != null" :src="state.kakaoPayUrl" style="width:100%; height:100%;"></webview></div>
                        </div>
                        <div>
                            <div>
                                <div>남은시간</div>
                                <div>
                                    {{ String(Math.floor(state.qr.remainTime/60) - Math.floor(state.qr.remainTime/3600)*60).padStart(2, '0') }}:{{ String(Math.floor(state.qr.remainTime) - Math.floor(state.qr.remainTime/60)*60).padStart(2, '0') }}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cancel-btn" @click="fnc.closeChargeWin()">
                충전 취소
            </div>
        </div>
        <div class="exit" @click="fnc.closeChargeWin()">
            x
        </div>

    </div>
</template>

<script>
let ipcRenderer;
import { nextTick } from 'vue';
import { getChildState } from 'element-plus/es/components/tree/src/model/node';
import { reactive, onMounted, onUnmounted, getCurrentInstance, ref } from 'vue';
if (window && window.process && window.process.type) {
  import('electron').then((electron) => {
    ipcRenderer = electron.ipcRenderer;
  })
};

export default {
    setup (){
        const { proxy } = getCurrentInstance();
        const store = proxy.$store;
        const router = proxy.$router;
        const comUtil = proxy.$comUtil;
        const axios = proxy.$axios;
        const state = reactive({
            userInfo : store.state.userInfo,
            activeCharge : null,
            feeCharge : [],
            chargeOrder : {},
            qr : {},
            qrInterval : null,
            kakaoPayUrl : null,
        })
        const aid = process.env.VUE_APP_aid;
        const cid = process.env.VUE_APP_cid;
        console.log(aid);
        onMounted(() => {
            fnc.getList();
        });

        onUnmounted(() => {

        });

        const fnc = {
            getList() {
                axios.get("/feeCharge")
                .then((res) => {
                    res.data.forEach((obj) => {
                        state.feeCharge.push({
                            id: obj.id,
                            name: obj.name,
                            time: obj.time,
                            price: comUtil.addComma(obj.price),
                        })
                    })
                }).catch((err) => {
                    console.log(err)
                })
            },
            closeChargeWin() {
                if (window && window.process && window.process.type) {
                    ipcRenderer.send('closeChargeWin');
                }else{
                    router.push({"path" : "/"});
                }
            },
            chargeInfo(item){
                if(state.qrInterval != null){
                    clearInterval(state.qrInterval);
                }
                state.chargeOrder = item;
                state.qr.remainTime = 120;
                state.qrInterval = setInterval(() => {
                    state.qr.remainTime -= 1;
                    if(state.qr.remainTime === 0){
                        clearInterval(state.qrInterval);
                    }
                }, 1000);
            },
            activateCharge(index){
                state.activeCharge = index;
            },
            payment(item){
                
                console.log(comUtil.getDate("yyyyMMdd"), new Date)
                const uuid = fnc.generateUUID();
                const headers = {
                    'Authorization' : 'KakaoAK '+ aid,
                    'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                };
                const body = {
                    'cid' : cid,
                    'partner_order_id' : uuid+comUtil.getDate("yyyyMMdd", new Date),
                    'partner_user_id' : state.userInfo.loginId,
                    'item_name' : state.chargeOrder.name,
                    'quantity' : 1,
                    'total_amount' : parseInt(comUtil.removeComma(state.chargeOrder.price)),
                    'tax_free_amount' : 0,
                    'approval_url' : 'https://localhost:8081/chargeOk',
                    'cancel_url' : 'https://localhost:8081/chargeCancel',
                    'fail_url' : 'https://localhost:8081/chargeFailed',
                }
                axios.post("https://kapi.kakao.com/v1/payment/ready", body ,{ headers })
                .then((res) => {
                    state.kakaoPayUrl = res.data.next_redirect_pc_url;
                    const qrWindow = window.open(state.kakaoPayUrl, '_blank', 'width=500,height=500,menubar=no,toolbar=no,location=no,status=no,frame=false');
                    /** TODO: SSL 추가 필요 */
                })
            },
            generateUUID() {
                const cryptoObj = window.crypto || window.msCrypto;
                const array = new Uint32Array(4);
                cryptoObj.getRandomValues(array);
                return array.join('');
            }
        }

        return {
            state
            , fnc
        }
    }
}
</script>