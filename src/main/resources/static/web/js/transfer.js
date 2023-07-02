const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            amount: 0,
            description: "",
            numberAccountOrigin: "",
            numberAccountDestiny: ""
        }
    },
    created(){

    },
    methods:{
      createTransfer(){
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
            console.log("transferencia hecha")
        })
        .catch(err => {
            if (err.response && err.response.data) {
                let errorMessage = err.response.data;
                if (errorMessage === "has unfilled fields") {
                    alert("Por favor complete todos los campos");
                } else if (errorMessage === "One or both account numbers do not exist in our database") {
                    alert("Una o ambas cuentas no existen en nuestra base de datos");
                } else if (errorMessage === "The account numbers must be different") {
                    alert("Los números de cuenta deben ser diferentes");
                } else if (errorMessage === "your account does not have enough funds to make the transfer") {
                    alert("Su cuenta no tiene suficientes fondos para realizar la transferencia");
                } else if (errorMessage === "The source account does not belong to the authenticated client") {
                    alert("La cuenta de origen no pertenece al cliente autenticado");
                } else if (errorMessage === "The transfer amount must be greater than 0") {
                    alert("El monto de la transferencia debe ser mayor que 0");
                }
            } else {
                console.error("salio todo para el carajo")
            }
        })
    } 
    }
})
app.mount("#app")