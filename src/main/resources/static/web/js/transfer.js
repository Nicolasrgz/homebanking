const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            amount: null,
            description: "",
            numberAccountOrigin: "",
            numberAccountDestiny: ""
        }
    },
    created(){

    },
    mounted() {
        new Cleave('#amount', {
          numeral: true,
          numeralThousandsGroupStyle: 'thousand',
          prefix: '$'
        });
      },
    methods:{
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
      createTransfer() {
        swal({
            title: "Are you sure?",
            text: "Are you sure you want to make this transfer?",
            icon: "warning",
            buttons: ["No", "Yes"],
            dangerMode: true,
        })
        .then((willTransfer) => {
            if (willTransfer) {
                // Obtén el valor del campo de texto
                this.amount = document.querySelector('#amount').value;

                // Elimina los caracteres no numéricos
                this.amount = this.amount.replace(/[^0-9]/g, '');

                // Convierte la cadena a un número
                this.amount = parseInt(this.amount);

                // El usuario confirmó la transferencia
                // Aquí va el código para realizar la transferencia
                const formData = new URLSearchParams();
                formData.append("amount", this.amount);
                formData.append("description", this.description);
                formData.append("numberAccountOrigin", this.numberAccountOrigin);
                formData.append("numberAccountDestiny", this.numberAccountDestiny);
    
                axios.post('/api/transactions', formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                    .then(res => {
                        swal({
                            title: "Success",
                            text: "Transfer completed",
                            icon: "success",
                            button: "OK"
                        }).then(() => {
                            window.location.href = "/web/pages/accounts.html";
                        });
                    })
                    .catch(err => {
                        if (err.response && err.response.data) {
                            let errorMessage = err.response.data;
                            if (errorMessage === "has unfilled fields") {
                                swal({
                                    title: "Error",
                                    text: "Please fill in all fields",
                                    icon: "error",
                                    button: "OK"
                                });
                            } else if (errorMessage === "One or both account numbers do not exist in our database") {
                                swal({
                                    title: "Error",
                                    text: "One or both accounts do not exist in our database",
                                    icon: "error",
                                    button: "OK"
                                });
                            } else if (errorMessage === "The account numbers must be different") {
                                swal({
                                    title: "Error",
                                    text: "The account numbers must be different",
                                    icon: "error",
                                    button: "OK"
                                });
                            } else if (errorMessage === "your account does not have enough funds to make the transfer") {
                                swal({
                                    title: "Error",
                                    text: "Your account does not have enough funds to make the transfer",
                                    icon: "error",
                                    button: "OK"
                                });
                            } else if (errorMessage === "The source account does not belong to the authenticated client") {
                                swal({
                                    title: "Error",
                                    text: "The source account does not belong to the authenticated client",
                                    icon: "error",
                                    button: "OK"
                                });
                            } else if (errorMessage === "The transfer amount must be greater than 0") {
                                swal({
                                    title: "Error",
                                    text: "The transfer amount must be greater than 0",
                                    icon: "error",
                                    button: "OK"
                                });
                            }
                        } else {
                            swal({
                                title: "Error",
                                text: "There was an error making your transfer",
                                icon: "error",
                                button: "OK"
                            });
                        }
                    })
            } else {
                // El usuario canceló la transferencia
                // No se realiza ninguna acción
            }
        });
    }
    
  },
      
})
app.mount("#app")