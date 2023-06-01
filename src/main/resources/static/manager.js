const { createApp } = Vue

const app = createApp({
  data() {
    return {
      clients: []
    };
  },
  created() {
    axios.get('http://localhost:8080/clients')
      .then(response => {
        this.clients = response.data;
        console.log(this.clients);
      })
      .catch(error => {
        console.error(error);
      });
  }
})

app.mount('#app')
