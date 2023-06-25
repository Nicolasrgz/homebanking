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
        
    },
})
app.mount("#app")