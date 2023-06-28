const{createApp} = Vue;

const app = createApp({
    data(){
        return{
          firstNameR: "",
          lastNameR: "",
          passwordR: "",
          emailR: "", 

          password: "",
          email: "",
        }
    },
    created(){
    },
methods: {
  resetLogin(){
    this.email = ""
    this.password = ""
  },
  resetRegister(){
    firstNameR= ""
    lastNameR= ""
    passwordR= ""
    emailR= ""
  },
  submit() {
    const formData = new URLSearchParams();
    formData.append('email', this.email);
    formData.append('password', this.password);

    axios.post('/api/login', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(response => {
      window.location.href = '/web/pages/accounts.html';
      this.resetLogin()
    })
    .catch(error => {
      alert('Su usuario no se encuentra registrado');
    });
  },
  submit2() {
    const formData = new URLSearchParams();
    formData.append('email', this.emailR);
    formData.append('password', this.passwordR);

    axios.post('/api/login', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(response => {

      window.location.href = '/web/pages/accounts.html';
    })
    .catch(error => {
      alert('Su usuario no se encuentra registrado');
    });
  },
  register() {

    const formDatas = new URLSearchParams();
    formDatas.append('firstName', this.firstNameR);
    formDatas.append('lastName', this.lastNameR);
    formDatas.append('email', this.emailR);
    formDatas.append('password', this.passwordR);

    axios.post('/api/clients', formDatas, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(response => {
      alert("your user was successfully registered")
      this.submit2()
      this.resetRegister()
    })
    .catch(error => {
      alert('Su usuario ya se encuentra registrado');
    });
  },
}
})
app.mount("#app")