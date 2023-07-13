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
            axios.get("http://localhost:8080/api/clients/current")
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
        createdAccount(){
          axios.post("/api/clients/current/accounts")
          .then(res => {
            alert("cuenta creada")
            window.location.href = '/web/pages/accounts.html'
          })
          .catch(err => alert("limite de cuentas alcanzado"))
        },
        redirection(){
          return window.location.href = "/web/pages/loan-application.html"
        },
        deleteAccount(event){
          // Obtener el ID de la tarjeta desde el atributo data-id del botón
          let accountId = event.target.getAttribute('data-id');
      
          axios.delete("/api/clients/current/accounts?id=" + accountId)
          .then(res =>{
            window.location.href = "/web/pages/accounts.html"
          })
          .catch(error => {
            // Inicio de sesión fallido
            // Mostrar mensaje de error al usuario
            alert('error');
          });
        }
    },
})

app.component('format-currency', {
  props: ['value'],
  template: `{{formattedValue}}`,
  computed: {
    formattedValue() {
      return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
      }).format(this.value);
    }
  }
})

app.mount("#app")
