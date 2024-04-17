<template>
    <div class="popOrder-container">
        <div class="order-wrapper">
            <div class="notice-wrapper">
                <div class="notice"> {{ state.notice }}</div>
                <div class="slider"></div>
            </div>
            <div class="goods-menu-area">
                <div class="goods-menu-wrapper">
                    <div v-for="(item, index) in state.menu"
                        @click="state.filter.typeCode = item.value; fnc.getList(); fnc.activateMenu(index)"
                        :class="{'active': state.activeMenu === index }"
                        :key="index">
                        <span v-html="item.label"></span>
                        <span :value="item.value"></span>
                    </div>
                    <div class="searchBox-area">
                        <el-input
                            v-model="state.filter.name"
                            @keyup.enter="fnc.getList()"
                            class="searchBox"
                            :placeholder="'상품명 검색'"
                        />
                        <div class="glass" @click="fnc.getList()">🔍</div>
                    </div>
                </div>
            </div>
            <div class="goods-container">
                <div class="goods-area">
                    <div class="goods-wrapper" v-for="(item, index) in state.goods" @click="fnc.orderInfo(item)">
                        <img />
                        <div v-html="item.foodName"></div>
                        <div v-html="item.foodPrice+'원'"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="payment-container">
            <div class="orderInfo-area">
                <div class="orderInfo-header">
                    <div>
                        <div>PC NO. {{ state.pcNum }}</div>
                        <div>{{ state.userInfo.name }}</div>
                    </div>
                    <div>
                        <div>
                            <div @click="fnc.closeOrderWin()">X</div>
                        </div>
                        <div>
                            <div>☰ 주문내역 보기</div>
                        </div>
                    </div>
                </div>
                <div class="orderInfo-container">
                    <div class="orderInfo-wrapper">
                        <div class="orderInfo" v-for="(item,index) in state.order">
                            <div>
                                <div v-html="item.foodName"></div>
                                <div @click="fnc.removeOrder(item)">X</div>
                            </div>
                            <div>
                                <div>
                                    <div class="numBtn" @click="fnc.calculateOrder(item, 'minus')">-</div>
                                    <div v-html="item.foodQuantity"></div>
                                    <div class="numBtn" @click="fnc.calculateOrder(item, 'plus')">+</div>
                                </div>
                                <div v-html="item.foodPrice+'원'"></div>
                            </div>
                        </div>
                        <div class="totalPrice">
                            <div>총 주문 금액</div>
                            <div> {{ state.totalPrice }}원 </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="banner-area">

            </div>
            <div class="payment-area">
                <div class="payBtn">
                    <el-button
                        v-for="(item, index) in state.payType"
                        type="primary"
                        :key="index"
                        :value="item.value"
                        @click="fnc.payType(item.value); fnc.activatePayType(index)"
                        :class="{'active': state.activePayType === index }"
                    > {{ item.label }} </el-button>
                </div>
                <div class="cashDetail" v-if="state.activePayType === 0">
                    <el-radio-group v-model="state.order.cashType">
                        <el-radio
                            v-for="(item, index) in state.detailfoodPrice"
                            :key="index"
                            :label="item.label"
                            :value="item.value"
                            >
                            <!-- <span v-if="item.label == '직접입력'">직접입력</span> -->
                            <span v-if="item.label == '직접입력'">직접입력
                                <el-input v-model="state.order.cashAmount"  class="directInput" :placeholder="'직접입력'"/>
                            </span>
                        </el-radio>
                    </el-radio-group>
                </div>
                <div class="request-wrapper">
                    <el-input 
                        v-model="state.order.request"
                        :placeholder="'요청사항은 50자까지 입력할 수 있습니다.'"
                        :maxlength="50"
                    />
                </div>
                <div class="orderBtn">
                    <el-button
                        type="warning"
                        @click="fnc.order()"
                    >주문하기</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
let ipcRenderer;
import { reactive, onMounted, onUnmounted, getCurrentInstance } from 'vue';
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
        const axios = proxy.$axios;
        const comUtil = proxy.$comUtil;
        const messageBox = proxy.$messageBox;
        const state = reactive({
            userInfo : store.state.userInfo,
            pcNum : store.state.pcNum,
            activeMenu : 0,
            activePayType: 0,
            notice : "특이사항 및 첨가소스는 상품에 마우스를 대면 써있습니다!",
            totalPrice: "0",
            filter: {},
            menu : [
                {
                    label: "추천상품",
                    value: "recommendGoods"
                },
                {
                    label: "ICIACafe",
                    value: "cafe"
                },
                {
                    label: "음료류",
                    value: "beverage"
                },
                {
                    label: "한끼의품격(밥)",
                    value: "rice"
                },
                {
                    label: "라면류",
                    value: "noodles"
                },
                {
                    label: "튀김류",
                    value: "fries"
                },
                {
                    label: "간식류",
                    value: "snacks"
                },
            ],
            search: {
                goodsName: ""
            },
            goods: [],
            order: [],
            payType:[
                {   label: "현금", value: "cash"},
                {   label: "신용카드", value: "credit"},
                {   label: "ICIA앱 결제", value: "app"},
                {   label: "카카오페이", value: "kakaoPay"},
                {   label: "포인트", value: "point"},
            ],
            detailfoodPrice: [
                {   label: "5만원", value: "50000"},
                {   label: "1만원", value: "10000"},
                {   label: "5천원", value: "5000"},
                {   label: "1천원", value: "1000"},
                {   label: "금액에 맞게", value: "fit"},
                {   label: "직접입력", value: "input"},
            ]
        })
        onMounted(async () => {
            state.menu = await fnc.getMenuList();
            fnc.getList();
            state.order.cashType = "금액에 맞게";
        });
        onUnmounted(() => {

        });
        const fnc = {
            async getMenuList() {
                let data = []
                const res = await axios.get("/code", { params: {
                    groupCode: "f100"
                }})
                res.data.forEach((obj) => {
                    data.push({ label:obj.name, value:obj.code })
                })
                state.filter.typeCode = res.data[0].code;
                return data;
            },
            getList() {
                if(state.filter.name != undefined){
                    state.activeMenu = null;
                }
                const params = Object.assign({}, state.filter);
                axios.get("/food", { params: params})
                .then((res) => {
                    state.goods = [];
                    res.data.forEach((obj) => {
                        state.goods.push({ id: obj.id, foodName: obj.name, foodPrice: comUtil.addComma(obj.price)});
                    })
                }).catch((err) => {
                    console.log(err);
                }).finally(() => {
                    state.filter = {};
                })
            },
            orderInfo(item) {
                if(!state.order.some(obj => obj.foodId === item.id)){
                    state.order.push({ foodId:item.id, foodName: item.foodName, foodPrice: item.foodPrice, foodQuantity: 1});
                }else {
                    let index = state.order.findIndex(obj => obj.foodId === item.id);
                    state.order[index].foodQuantity += 1;
                    if(!state.order[index].foodPrice.isNaN){
                        state.order[index].foodPrice = parseInt(comUtil.removeComma(state.order[index].foodPrice));
                        state.order[index].foodPrice += parseInt(comUtil.removeComma(item.foodPrice));
                        state.order[index].foodPrice = comUtil.addComma(state.order[index].foodPrice);
                    }
                }
                if(!state.totalPrice.isNaN){
                    /* 자꾸 왔다갔다 콤마 뺏다넣었다하는부분 추후 수정 예정 */
                    state.totalPrice = parseInt(comUtil.removeComma(state.totalPrice));
                    state.totalPrice += parseInt(comUtil.removeComma(item.foodPrice));
                    state.totalPrice = comUtil.addComma(state.totalPrice);
                }

            },
            removeOrder(item) {
                state.totalPrice = comUtil.addComma(comUtil.removeComma(state.totalPrice) - comUtil.removeComma(state.order[state.order.findIndex(obj => obj.foodName === item.foodName)].foodPrice));
                state.order.splice(state.order.findIndex(obj => obj.foodName === item.foodName), 1);
            },
            calculateOrder(item, type){
                if(type === "plus"){
                    item.foodQuantity += 1
                    item.foodPrice = parseInt(comUtil.removeComma(item.foodPrice));
                    item.foodPrice += parseInt(comUtil.removeComma(state.goods[state.goods.findIndex(obj => obj.foodName === item.foodName)].foodPrice));
                    item.foodPrice = comUtil.addComma(item.foodPrice);
                    state.totalPrice = parseInt(comUtil.removeComma(state.totalPrice));
                    state.totalPrice += parseInt(comUtil.removeComma(state.goods[state.goods.findIndex(obj => obj.foodName === item.foodName)].foodPrice));
                    state.totalPrice = comUtil.addComma(state.totalPrice);
                }else if(type === "minus" && item.foodQuantity > 1) {
                    item.foodQuantity -= 1
                    item.foodPrice = parseInt(comUtil.removeComma(item.foodPrice));
                    item.foodPrice -= parseInt(comUtil.removeComma(state.goods[state.goods.findIndex(obj => obj.foodName === item.foodName)].foodPrice))
                    item.foodPrice = comUtil.addComma(item.foodPrice);
                    state.totalPrice = parseInt(comUtil.removeComma(state.totalPrice));
                    state.totalPrice -= parseInt(comUtil.removeComma(state.goods[state.goods.findIndex(obj => obj.foodName === item.foodName)].foodPrice));
                    state.totalPrice = comUtil.addComma(state.totalPrice);
                }
            },
            closeOrderWin() {
                if (window && window.process && window.process.type) {
                    ipcRenderer.send('closeOrderWin');
                }else{
                    router.push({"path" : "/"});
                }
            },
            order(){
                if(state.order.length < 1){
                    return messageBox.alert("주문하실 상품을 선택해주세요.");
                }else if(state.order.payType == ""|| state.order.payType == undefined){
                    return messageBox.alert("지불하실 선불권종을 선택해주세요.");
                }

                let foodOrderDetail= []
                state.order.forEach((obj) => {
                    foodOrderDetail.push({ foodId: obj.foodId ,foodName:obj.foodName, foodPrice:comUtil.removeComma(obj.foodPrice), foodQuantity: obj.foodQuantity })
                })
                const params = {
                    memberId : state.userInfo.id,
                    pcNum : state.pcNum,
                    totalPrice : comUtil.removeComma(state.totalPrice),
                    request : state.order.request,
                    payType : state.order.payType,
                    foodOrderDetailList : foodOrderDetail,
                }
                axios.post("/order", params)
                    .then((res) => {
                        messageBox.alert("주문이 접수되었습니다.");
                    })
                    .catch((err) => {
                        messageBox.alert("카운터에 문의해주세요");
                    })
                    .finally(() => {
                        state.order = [];
                        state.totalPrice = "0";
                        state.activePayType = "0";
                    })
            },
            payType(payType){
                state.order.payType = payType; 
            },
            activateMenu(index){
                state.activeMenu = index;
            },
            activatePayType(index){
                state.activePayType = index;
            }
        }

        return {
            state
            , fnc
        }
    }
}
</script>