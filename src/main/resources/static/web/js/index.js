const{createApp} = Vue;

const app = createApp({
    data(){
        return{
            password: "",
            email: "",
        }
    },
    created(){
    },
methods: {
  submit() {
    // Codificar los datos del formulario
    const formData = new URLSearchParams();
    formData.append('email', this.email);
    formData.append('password', this.password);

    // Realizar petición de inicio de sesión
    axios.post('/api/login', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(response => {
      // Inicio de sesión exitoso
      // Redireccionar a accounts.html
      window.location.href = '/web/pages/accounts.html';
    })
    .catch(error => {
      // Inicio de sesión fallido
      // Mostrar mensaje de error al usuario
      alert('Su usuario no se encuentra registrado');
    });
  },

}


})
app.mount("#app")