const {createApp} = Vue 

const app = createApp({
    data(){
        return{
            account: null,
            params: {},
            identificador: [],
            accounts: [],
            accountPrimary: []
        }
    },
   created(){
    this.params = new URLSearchParams(location.search)
    this.identificador = this.params.get('id')
    this.accountInfo()
   },
   methods:{
   accountInfo(){
    axios.get("http://localhost:8080/api/accounts")
    .then(response => {
        this.accounts = response.data
        this.account = this.accounts.find(a => a.id == this.identificador)
        document.title = `details of ${this.account}`
        console.log(this.accounts)
    })
    .catch(err => {
        console.error(err);
      });
   }
   },

})

app.mount('#app')