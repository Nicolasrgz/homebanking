const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            color: [],
            type:[],
        }
    },
    methods:{
        createCard(){
            const formDatas = new URLSearchParams();
            formDatas.append('type', this.color);
            formDatas.append('color', this.type); 
            axios.post('/api/clients/current/cards', formDatas, {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              }
            })
            .then(response => {
              alert("your user was successfully registered")
              window.location.search = "web/pages/cards.html"  
            })
            .catch(error => {
              alert('excede su limite de creacion de tarjetas');
            });
        }
    }
})
app.mount("app");