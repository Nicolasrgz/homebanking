const {createApp} = Vue 

const app = createApp({
    data(){
        return{
            account: [],
            params: {},
            identificador: '',
            accountsP: [],
            isActive: true,
            hasError: false
        }
    },
   created(){
    this.accountInfo()
   },
   methods:{
   accountInfo(){
    this.params = new URLSearchParams(location.search)
    this.identificador = this.params.get('id')
    axios.get(`http://localhost:8080/api/accounts/${this.identificador}`)
    .then(response => {
        this.accountsP = response.data
        this.account = this.accountsP.accounts
        document.title = `details of ${this.accountsP.number}`
        console.log(this.account)
    })
    .catch(err => {
        console.error(err);
      });
   }
   },
})

app.mount('#app')
