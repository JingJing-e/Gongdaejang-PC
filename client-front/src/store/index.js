import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
    state: {
        userInfo : {},
        remainTime: 0,
        pcNum: null,
        usedTime: 0,
    },
    mutations: {
        setUserInfo: (state, userInfo) => {
            state.userInfo = userInfo;
        },
        logOut: (state) => {
            state.userInfo = {};
        },
        setRemainTime: (state, remainTime) => {
            state.remainTime = remainTime;
        },
        setUsedTime: (state, usedTime) => {
            state.usedTime = usedTime;
        },
        setPcNum: (state, pcNum) => {
            state.pcNum = pcNum;
        }
    },
    actions: {

    },
    modules: {

    },

    plugins: [
        createPersistedState({
            storage: window.localStorage,
            key: "icia",
            paths: ["userInfo", "remainTime", "pcNum", "usedTime"],
        })
    ]
})