const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            amount: null,
            name: "",
            maxAmount: null,
            payments: []
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
        createdLoan(){
            this.amount = document.querySelector('#amount').value;
    
            // Elimina los caracteres no numéricos
            this.amount = this.amount.replace(/[^0-9]/g, '');
    
            // Convierte la cadena a un número
            this.amount = parseInt(this.amount);
    
            // El usuario confirmó la transferencia
            // Aquí va el código para realizar la transferencia
            const formData = new URLSearchParams();
            formData.append("name", this.name);
            formData.append("maxAmount", this.amount);
            formData.append("payments", this.payments);
            axios.post('http://localhost:8080/api/loans/created', formData, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
        .then(res => {
            alert("prestamo creado")
            window.location.href = "/web/pages/loan-admin.html"
        })
        .catch(err => alert("error"))
        }
    },
    
})
app.mount("#app")
