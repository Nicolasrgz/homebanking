const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            selectType: "",
            selectPayments: "",
            loansPayments: {},
            loans: {},
            amount: 0,
        }
    },
    created(){
        this.loansData()
    },
    methods:{
        loansData(){
            axios.get("http://localhost:8080/api/loans")
            .then(res => {
                this.loans = res.data
                console.log(this.loans)
            })
        },
    }
})
app.mount("#app")
