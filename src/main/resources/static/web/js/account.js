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
            })
            .catch((err) => {
              console.error(err);
            });
        },
        LogOut(){
          axios.post('/api/logout')
          .then(response => {
            // Inicio de sesión exitoso
            // Redireccionar a accounts.html
            window.location.href = '/web/index.html';
          })
          .catch(error => {
            // Inicio de sesión fallido
            // Mostrar mensaje de error al usuario
            alert('Error al iniciar sesión');
          });
        },
   },
   computed: {
    sortedAccount() {
        return this.account.slice().sort((a, b) => b.id - a.id);
    }
}

})

app.mount('#app')
