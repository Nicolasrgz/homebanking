const {createApp} = Vue

const app = createApp({
    data(){
        return{
            melba: [],
            melbaAccounts: []
        }
    },
    created(){
        this.user()
    },
    methods:{
        user(){
            axios.get("http://localhost:8080/api/clients/1")
            .then(response => {
                this.melba = response.data
                this.melbaAccounts = response.data.accounts
                console.log(this.melbaAccounts)     
            })
            .catch(err => {
                console.error(err);
              });
        }
    },
    computed:{

    }
})
app.mount("#app")

