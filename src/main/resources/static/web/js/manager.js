const { createApp } = Vue

const app = createApp({
  data() {
    return {
      clients:[],
      json: [],
      newclient: {name: "" ,lastName: "" ,email: ""}
    };
  },
  created() {
    this.loadData()

  },
  methods: {
    
    loadData(){
         axios.get('http://localhost:8080/rest/clients')
              .then(response => {
                this.json = response.data;
                this.clients = response.data._embedded.clients
                console.log(this.json)
                console.log(this.clients)
              })
              .catch(err => {
                console.error(err);
              });
    },
    addClients(){
      if(this.newclient !== "") {
        this.postClient()
      }
    },
    postClient(){
      axios.post("http://localhost:8080/rest/clients", {
        firstName: this.newclient.name,
        lastName: this.newclient.lastName,
        email: this.newclient.email
      })
      .then(res => {
        this.loadData()
        this.reset()
      })
      .catch(err =>{
        console.log(err)
      })
      
    },
    reset(){
      this.newclient.name = ""
      this.newclient.lastName = ""
      this.newclient.email = ""
    },

    deleteUser(id){
      axios.delete(id)
      .then(res => {
        console.log(res)
        console.log("user delete")
        this.loadData()
      })
      .catch(err => console.log(err))
    }

  }
})

app.mount('#app')
