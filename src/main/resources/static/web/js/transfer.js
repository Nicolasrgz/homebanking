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
            const formData = new URLSearchParams;
            formData.append = ("amount", this.amount)
            formData.append = ("description", this.description)
            formData.append = ("numberAccountOrigin", this.numberAccountOrigin)
            formData.append = ("numberAccountDestiny", this.numberAccountDestiny)

            axios.post('/api/transactions', formData, {
                headers: {
                  'Content-Type': 'application/x-www-form-urlencoded'
                }
              })
              .then(res => {
                console.log("transferencia hecha")
              })
              .catch(err => console.error("salio todo para el carajo"))
        }
    }
})
app.mount("#app")