const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            color: "",
            type: "",
        }
    },
    created(){

    },
    methods:{
        createCard(){
            const formDatas = new URLSearchParams();
            formDatas.append('type', this.type);  
            formDatas.append('color', this.color); 
            console.log('color:', this.color);
            console.log('type:', this.type);          
            axios.post('/api/clients/current/cards', formDatas, {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              }
            })
            .then(response => {
              alert("your user was successfully registered")
              window.location.href = "/web/pages/cards.html"  
 
            })
            .catch(error => {
              alert('excede su limite de creacion de tarjetas');
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
    }
})
app.mount("#app");