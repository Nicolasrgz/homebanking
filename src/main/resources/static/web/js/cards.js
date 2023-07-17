const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            client: {},
            card: [],
            type: [],
        }
    },
    created(){
        this.user()
    },
    methods:{
        user(){
            axios.get("http://localhost:8080/api/clients/current")
            .then(response => {
                this.client = response.data
                this.card = this.client.cards
                this.type = this.card.type
            
                console.log(this.card)
                
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
          deleteCard(cardId) {
            axios.patch(`/api/card/${cardId}/deactivate`)
              .then(res => {
                alert("logrado")
                window.location.href = "/web/pages/cards.html"
              })
              .catch(err => {
                alert("error")
              })
          }
          
    },
})
app.mount("#app")
