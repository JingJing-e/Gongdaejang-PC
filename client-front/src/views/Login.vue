<template>
    <div class="loginTop">
        <div class="imgArea">
            <!-- <img src="~@/assets/images/login.jpeg" /> -->
            <span class="pcNum" v-if="state.pcNum.set != ''">{{ state.pcNum.set }}</span>
            <!-- <div v-if="state.pcNum.set == ''">피씨 번호를 입력해주세요.</div> -->
        </div>
    </div>
    <div class="loginBottom">
        <div class="loginWrap">
            <div class="leftBox">
                <div class="qrArea">
                    <!-- <img src="~@/assets/images/qrImage.jpeg" /> -->
                </div>
            </div>
            <div class="rightBox">
                <div class="inputArea">
                    <div class="memberBtnArea">
                        <div class="memberBtnWrapper">
                            <div class="tgBtn" @click="fnc.loginToggleBtn">회원 로그인</div>
                            <div class="tgBtn" @click="fnc.loginToggleBtn">비회원 로그인</div>
                        </div>
                    </div>
                    <el-form>
                        <el-form-item prop="loginId" v-if="state.memberYn == 'Y'">
                            <label>아이디</label>
                            <el-input v-model="state.loginForm.loginId" class="loginInput" prefix-icon="el-icon-user-filled"
                                placeholder="아이디" autuocomplete="off" autofocus="true" @keyup.enter="fnc.login()"/>
                        </el-form-item>
                        <el-form-item prop="loginPassword" v-if="state.memberYn == 'Y'">
                            <label>패스워드</label>
                            <el-input v-model="state.loginForm.password" class="loginInput" prefix-icon="el-icon-lock"
                                placeholder="패스워드" type="password" autuocomplete="off" @keyup.enter="fnc.login()"/>
                        </el-form-item>
                        <el-form-item prop="cardNum" v-if="state.memberYn == 'N'">
                            <label>비회원 / 카드번호</label>
                            <el-input v-model="state.loginForm.cardNum" class="loginInput" prefix-icon="el-icon-lock"
                                placeholder="비회원/카드번호" autuocomplete="off" @keyup.enter="fnc.login()"/>
                        </el-form-item>
                    </el-form>
                    <div class="loginBtnArea">
                        <el-button class="loginBtn" @click="fnc.login()">로그인</el-button>
                    </div>
                    <div class="etcBtnArea">
                        <el-button @click="fnc.popJoinForm()">회원가입</el-button>
                        <el-button> PC 시간 충전 </el-button>
                    </div>
                </div>
                <div class="textboxArea">
                    공지사항
                </div>
                <div class="btnArea">
                    <div class="btnWrap">
                        <el-button @click="fnc.turnOff('shutdown')"></el-button>
                        <el-button @click="fnc.turnOff('restart')"></el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <el-dialog class="join-pop" v-model="state.popJoinForm.pop" :title="'회원정보 등록'" :center="true" :close-on-click-modal="false" :width="'30%'">
        <PopJoinForm 
        v-if="state.popJoinForm.pop"
        @cancel="fnc.cancelJoinPop()"
        />
    </el-dialog>
    <el-dialog v-model="state.pcNum.pop" :title="'피씨 번호를 입력해주세요'">
        <el-input v-if="state.pcNum.pop" v-model="state.pcNum.pcNum"/>
        <el-button v-if="state.pcNum.pop" @click="fnc.addPcNum">저장</el-button>
    </el-dialog>
</template>

<script>
let ipcRenderer;
import { reactive, onMounted, onUnmounted, getCurrentInstance, watch, computed } from 'vue';
import PopJoinForm from '@/views/PopJoinForm.vue'
if (window && window.process && window.process.type) {
  import('electron').then((electron) => {
    ipcRenderer = electron.ipcRenderer;
  })
};
import { useRoute } from 'vue-router';

export default {
    components : {
        PopJoinForm
    },
    setup(){
        const route = useRoute();
        const { proxy } = getCurrentInstance();
        const store = proxy.$store;
        const router = proxy.$router;
        const axios = proxy.$axios;
        const comUtil = proxy.$comUtil;
        const messageBox = proxy.$messageBox;
        
        const state = reactive({
            memberYn: 'Y',
            loginForm: {
                loginId: "",
                password: "",
                cardNum: "",
            },
            popJoinForm: {
                pop : false,
            },
            toPath: computed(() => comUtil.isEmpty(route.params.toPath) ? "/" : route.params.toPath),
            pcNum: {
                pop: false,
                pcNum: "",
                set: "",
            }
        })
        onMounted(() => {
            if (window && window.process && window.process.type) {
                fnc.readPcNum();
                setTimeout(function(){
                    if(state.pcNum.set == undefined || state.pcNum.set == "" || state.pcNum.set == null){
                        state.pcNum.pop = true;
                    }
                }, 1000);
            }
        });

        onUnmounted(() => {

        });
        watch(state.memberYn, (newValue, oldValue)=> {
            console.log(newValue);
            console.log(oldValue);
        })

        const fnc = {
            goPage(item) {
                router.push({ "path" : item });
            },
            login() {
                if((state.loginForm.loginId == "" || state.loginForm.loginId == null) && state.memberYn == "Y"){
                    return messageBox.alert("아이디를 입력해주세요.");
                }else if((state.loginForm.password == "" || state.loginForm.password == null) && state.memberYn == "Y"){
                    return messageBox.alert("비밀번호를 입력해주세요.");
                }else if((state.loginForm.cardNum == "" || state.loginForm.cardNum == null) && state.memberYn == "N")
                    return messageBox.alert("카드번호를 입력해주세요.");
                axios.post(`/member/login`, {
                    loginId: state.loginForm.loginId,
                    password: state.loginForm.password,
                    cardNum: state.loginForm.cardNum,
                }).then((res) => {
                    if(res.data.remainTime > 0){
                        axios.post(`/log`, {
                            memberId: res.data.id,
                            pcNum: 1,
                            typeCode: "l10001"
                        }).then((res2) => {
                            store.commit("setUserInfo", res.data);
                            store.commit("setRemainTime", res.data.remainTime);
                            if (window && window.process && window.process.type) {
                                ipcRenderer.send("createLoginWindow");
                            } else {
                                fnc.goPage("/")
                            }
                        })
                    }else if(res.data.remainTime <= 0){
                        messageBox.alert("선불 이용시간이 모두 소진되었습니다. 충전 후 이용해주세요.")
                    }else {
                        messageBox.alert("아이디 또는 비밀번호를 확인해주세요.");

                    }
                })
            },
            loginToggleBtn(e) {
                const element = document.getElementsByClassName("tgBtn");
                for(const a of element){
                    console.log(a);
                    a.style.color="#4f4f4f";
                    a.style.background="#343434";
                }
                if(e.target.innerText === '비회원 로그인'){
                    state.memberYn = 'N'
                    e.target.style.color="white";
                    e.target.style.background="#4f4f4f"
                }else {
                    state.memberYn = 'Y'
                    e.target.style.color="white";
                    e.target.style.background="#4f4f4f"
                }
            },
            turnOff(item) {
                // parent.window.open('about:blank','_self').close();
                // const turnoff = item;
                // axios.post(`/login/shutdown/${turnoff}`);
                let msg;
                if(item == "shutdown"){
                    msg = "종료";
                }else{
                    msg = "다시시작";
                }
                messageBox.confirm(`${msg}하시겠습니까?`).then(() => {
                    ipcRenderer.send('confirmAndShutdown', item);
                });
            },
            popJoinForm() {
                state.popJoinForm.pop = true;
            },
            cancelJoinPop() {
                state.popJoinForm.pop = false;
            },
            addPcNum() {
                if (window && window.process && window.process.type) {
                    ipcRenderer.send('saveToFile', { pcNum : state.pcNum.pcNum });
                    state.pcNum.pop = false;
                    fnc.readPcNum();
                }
            },
            readPcNum() {
                ipcRenderer.send("readFromFile");
                ipcRenderer.on('fileData', (event, data) => {
                    store.commit("setPcNum", data);
                    state.pcNum.set = store.state.pcNum;
                });
            }
        }

        return {
            state
            , fnc
        }
    }
}
</script>