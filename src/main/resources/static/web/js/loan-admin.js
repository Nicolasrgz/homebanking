const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            name: "",
            maxAmount: null,
            payments: []
        }
    },
    created(){

    },
    mounted() {
        new Cleave('#amount', {
          numeral: true,
          numeralThousandsGroupStyle: 'thousand',
          prefix: '$'
        });
      },
    methods:{
        createdLoan(){
            axios.post("http://localhost:8080/api/loans/created")
            .then(res => {

            })
            .catch(err => console.error(err))
        }
    },
})
app.mount("#app")