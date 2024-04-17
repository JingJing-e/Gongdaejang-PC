<template>
    <div>게임창</div>
</template>

<script>
import { reactive, onMounted, onUnmounted, getCurrentInstance } from 'vue';

export default {
    setup (){
        const { proxy } = getCurrentInstance();
        // const store = proxy.$store;
        const router = proxy.$router;
        const axios = proxy.$axios;
        const state = reactive({
        })

        onMounted(() => {

        });

        onUnmounted(() => {

        });

        const fnc = {

        }

        return {
            state
            , fnc
        }
    }
}
</script>