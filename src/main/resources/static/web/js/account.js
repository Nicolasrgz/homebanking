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
                  console.log(this.account)
              })
              .catch((err) => {
                  swal({
                      title: 'Error',
                      text: 'An error occurred while retrieving account information',
                      icon: 'error',
                      button: 'OK'
                  })
                  .then(()=>{
                    window.location.href = "/web/pages/accounts.html"
                  })
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
        generatedPdf(id) {
          axios.get(`/api/transactions/${id}/pdf`, { responseType: 'blob' })
            .then(res => {
              swal({
                title: "Success",
                text: "your pdf has been sent to your email, please check your mailbox",
                icon: "success",
                button: "OK"
            })
            })
            .catch(error => {
              alert('Error');
            });
        }
        
   },
   computed: {
    sortedAccount() {
        return this.account.slice().sort((a, b) => b.id - a.id);
    },
},

})

app.mount('#app')
