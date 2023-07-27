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
                this.card = this.client.cards.filter(cards => cards.active == false)
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
            swal({
              title: "Are you sure?",
              text: "Are you sure you want to create an account?",
              icon: "warning",
              buttons: ["No", "Yes"],
              dangerMode: true,
          }).then(res=>{
            axios.patch(`/api/card/${cardId}/deactivate`)
            .then(res => {swal({
              title: 'Success',
              text: 'card deleted',
              icon: 'success',
              button: 'OK'
            }).then(res=>{
              window.location.href = "/web/pages/cards.html"
            }) 
            })
            .catch(err => {
              swal({
                title: 'error',
                text: 'error',
                icon: 'error',
                button: 'OK'
              })
            })
          })

          },
          redirection(){
            return window.location.href = '/web/pages/create-cards.html';
          }
          
    },
})
app.mount("#app")
