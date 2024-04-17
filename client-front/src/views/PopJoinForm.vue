<template>
    <el-row class="join-wrap">
        <el-col>
            <el-form-item label="아이디(필수)" label-width="138px">
                <el-input 
                    v-model="state.form.loginId"
                    :autofocus="true"
                    placeholder="아이디를 입력해 주세요."
                    clearable
                    :formatter="
                        (value) =>
                        value.replace(/[^A-Za-z0-9]/, '')
                    "
                    :disabled="state.form.confirmId"
                />
                <el-button v-if="!state.form.confirmId" class="confirm-btn" @click="fnc.confirmId()">중복체크</el-button>
            </el-form-item>
        </el-col>
        <el-col>
            <el-form-item label="이름(필수)" label-width="138px">
                <el-input 
                    v-model="state.form.name"
                    placeholder="이름을 입력해 주세요."
                    clearable
                />
            </el-form-item>
        </el-col>
        <el-col>
            <el-form-item label="비밀번호(필수)" label-width="138px">
                <el-input
                    v-model="state.form.password"
                    placeholder="영문,숫자 조합으로 10자 이상 입력해 주세요."
                    type="password"
                    clearable
                />
            </el-form-item>
            <el-form-item label="비밀번호 확인(필수)" label-width="138px">
                <el-input
                    v-model="state.form.passwordConfirm"
                    placeholder="위의 비밀번호와 동일하게 입력해 주세요."
                    type="password"
                    clearable
                />
            </el-form-item>
        </el-col>
        <el-col>
            <el-row class="row">
                <el-form-item label="생년월일(필수)" label-width="138px">
                    <el-input
                        v-model="state.form.birthDate"
                        placeholder="ex) 19991010, 숫자8자리"
                        clearable
                    />
                </el-form-item>
                <el-form-item class="gender" label="성별(필수)" label-width="90px">
                    <el-radio-group v-model="state.form.genderCode">
                        <el-radio label="110001">남자</el-radio>
                        <el-radio label="110002">여자</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-row>
        </el-col>
        <el-col>
            <el-form-item label="휴대폰 번호(필수)" label-width="138px">
                <el-input
                    v-model="state.form.mobilePhoneNum"
                    placeholder="'-'없이 숫자를 직접 입력하세요."
                    clearable
                    :formatter="
                        (value) =>
                        value.replace(/[^0-9]/, '')
                    "
                    :maxlength="11"
                    :disabled="state.form.cert"
                />
                <el-button v-if="!state.form.cert" class="confirm-btn" @click="fnc.phoneCert()">본인인증</el-button>
            </el-form-item>
        </el-col>
        <el-col class="deputy">
            <el-form-item label="법정대리인 휴대폰번호" label-width="138px" :disabled="true">
                <el-input
                    v-model="state.form.deputyPhoneNum"
                    clearable
                    :disabled="true"
                />
            </el-form-item>
        </el-col>
        <el-col>
            <el-form-item label="주소" label-width="138px">
                <el-input
                    v-model="state.form.address"
                    placeholder="주소를 입력해 주세요."
                    clearable
                />
            </el-form-item>
        </el-col>
    </el-row>
    <div class="btn-area">
        <div class="btn-wrap" v-if="state.form.cert">
            <el-button
                :type="'primary'"
                @click="fnc.join()"
            >회원가입</el-button>
            <el-button
                :type="'danger'"
                @click="fnc.cancel()"
            >취소</el-button>
        </div>
        <div class="btn-wrap" v-if="!state.form.cert">
            <el-button
                class="cert"
                :disabled="true"
            >인증이 필요합니다</el-button>
        </div>
    </div>
</template>

<script>
import { reactive, onMounted, onUnmounted, getCurrentInstance } from 'vue';

export default {
    setup (props, { emit }){
        const { proxy } = getCurrentInstance();
        // const store = proxy.$store;
        const router = proxy.$router;
        const axios = proxy.$axios;
		const message = proxy.$message;
        const messageBox = proxy.$messageBox;
        const state = reactive({
            form : {
                loginId : "",
                name : "",
                password : "",
                passwordConfirm : "",
                birthDate : "",
                genderCode : "110001",
                mobilePhoneNum : "",
                cert : false,
                confirmId : false,
            }
        })

        onMounted(() => {

        });

        onUnmounted(() => {

        });

        const fnc = {
            join() {
                const loginIdReg = /^[A-Za-z0-9]+$/;
                const passReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@#$%^&+=!`~()-*]{10,}$/;
                const birthReg = /^\d{8}$/;
                const phoneReg = /^(01[016789])[-\s]?\d{3,4}[-\s]?\d{4}$/;

                if(state.form.loginId.trim() == "" || state.form.loginId == null){
                    return messageBox.alert("아이디를 입력해주세요.");
                }else if(state.form.name.trim() == "" || state.form.name == null){
                    return messageBox.alert("이름을 입력해주세요.");

                }else if(state.form.password.trim() == "" || state.form.name == null){
                    return messageBox.alert("비밀번호를 입력해주세요.");
                }else if(state.form.birthDate.trim() == "" || state.form.birthDate == null){
                    return messageBox.alert("생년월일을 입력해주세요.");
                }

                if(!state.form.confirmId){
                    return messageBox.alert("아이디 중복체크를 해주세요.");
                }else if(!passReg.test(state.form.password.trim())){
                    return messageBox.alert("비밀번호는 영문,숫자 조합으로 10자 이상 입력해야 합니다.");
                }else if(state.form.password !== state.form.passwordConfirm){
                    return messageBox.alert("비밀번호가 일치하지 않습니다.");
                }else if(!birthReg.test(state.form.birthDate)){
                    return messageBox.alert("생년월일을 정확히 입력해주세요.");
                }else if(!state.form.cert){
                    return messageBox.alert("휴대폰 번호를 정확히 입력해주세요.");
                }
                const param = state.form;
                axios.post(`/member/join`, param)
                .then((res) => {
                    messageBox.alert("회원가입 되었습니다.");
                    emit("cancel", false);
                })
            },
            confirmId() {
                const loginIdReg = /^[A-Za-z0-9]+$/;
                if(state.form.loginId.trim() == "" || state.form.loginId == null){
                    return;
                }
                if(!loginIdReg.test(state.form.loginId)){
                    return messageBox.alert("아이디는 영문, 숫자만 입력가능합니다.");
                }

                axios
                .get(`/member/confirmId`, {params : { loginId : state.form.loginId }})
                .then((res) => {
                    if(res.data == 0){
                        messageBox.alert("사용가능한 아이디입니다.");
                        state.form.confirmId = true;
                    }else {
                        messageBox.alert("사용할 수 없는 아이디입니다.");
                    }
                })
            },
            phoneCert(){
                const phoneReg = /^01[016789][0-9]{7,8}$/;
                if(!phoneReg.test(state.form.mobilePhoneNum)){
                    return messageBox.alert("휴대폰 번호를 정확히 입력해주세요.");
                }

                messageBox.alert("인증되었습니다");
                state.form.cert = true;
            },
            cancel(){
                emit("cancel", false);
            }
        }

        return {
            state
            , fnc
        }
    }
}
</script>