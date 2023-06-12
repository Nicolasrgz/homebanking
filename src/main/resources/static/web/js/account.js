const {createApp} = Vue 

const app = createApp({
    data(){
        return{
            account: [],
            params: {},
            identificador: '',
            accountsP: [],
            accountSort: [],
            loading: true
        }
    },
    created() {
        this.loading = false
        this.accountInfo();
      },
      methods: {
        accountInfo() {
          this.params = new URLSearchParams(location.search);
          this.identificador = this.params.get('id');
          axios
            .get(`http://localhost:8080/api/accounts/${this.identificador}`)
            .then((response) => {
              this.accountsP = response.data;
              this.account = this.accountsP.accounts;
              this.accountSort = this.account.sort((a, b) => a.id - b.id);
              document.title = `details of ${this.accountsP.number}`;
              console.log(this.account);
            })
            .catch((err) => {
              console.error(err);
            });
        }
      
   },
})

app.mount('#app')
