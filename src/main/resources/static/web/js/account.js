const {createApp} = Vue 

const app = createApp({
    data(){
        return{
            account: [],
            params: {},
            identificador: '',
            accountsP: [],
            accountSort: [],
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
              this.balance = this.accountsP.balance
              console.log(this.accountsP)
            })
            .catch((err) => {
              console.error(err);
            });
        },
        LogOut(){
          axios.post('/api/logout')
          .then(response => {
            window.location.href = '/web/index.html';
          })
          .catch(error => {
            alert('Error al iniciar sesión');
          });
        },
   },
   computed: {
    sortedAccount() {
        return this.account.slice().sort((a, b) => b.id - a.id);
    },
},

})

app.mount('#app')
