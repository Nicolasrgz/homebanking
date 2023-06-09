const {createApp} = Vue

const app = createApp({
    data(){
        return{
            melba: [],
            melbaAccounts: [],
            melbaSort: []
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
                this.melbaSort = this.melbaAccounts.sort((a,b)=> a.id - b.id)
                console.log(this.melbaSort)     
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

