const {createApp} = Vue

const app = createApp({
    data(){
        return{
            melba: [],
            melbaAccounts: [],
            melbaSort: [],
            melbaLoans: [],
            loansSort: []
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
                this.melbaAccounts = this.melba.accounts
                this.melbaSort = this.melbaAccounts.sort((a,b)=> a.id - b.id)
                this.melbaLoans = this.melba.loans
                this.loansSort = this.melbaLoans.sort((a,b)=> a.id - b.id)
                console.log(this.melba)
                console.log(this.melbaSort)     
                console.log(this.melbaLoans)
                console.log(this.loansSort)
            })
            .catch(err => {
                console.error(err);
              });
        }
    },
})
app.mount("#app")

