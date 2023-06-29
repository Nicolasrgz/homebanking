const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            melba: [],
            card: [],
            type: [],
        }
    },
    created(){
    this.user()
    },
    methods:{
        user(){
            axios.get("http://localhost:8080/api/clients/1")
            .then(response => {
                this.melba = response.data
                this.card = this.melba.cards
                this.type = this.card.type
            
                console.log(this.card)
                
            })
            .catch(err => {
                console.error(err);
              });
        },
        
    },
})
app.mount("#app")