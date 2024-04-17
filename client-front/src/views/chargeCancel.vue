<template>
    <div></div>
</template>

<script>
export default {
    setup (){
        const { proxy } = getCurrentInstance();
        // const store = proxy.$store;
        const router = proxy.$router;
        const axios = proxy.$axios;
        const state = reactive({
        })

        onMounted(() => {
            window.close();
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