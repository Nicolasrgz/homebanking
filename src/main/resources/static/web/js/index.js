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
      swal({
        title: 'Success',
        text: 'successful login',
        icon: 'success',
        button: 'OK'
      }).then(res=>{
        if (this.email === "admin@gmail.com") {
          window.location.href = '/web/pages/loan-admin.html';
        } else {
          window.location.href = '/web/pages/accounts.html';
        }
        this.resetLogin();
      })
    })
    .catch(err => swal({
      title: 'Error',
      text: 'Account no exist',
      icon: 'error',
      button: 'OK'
  }));
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
    .then(response => { swal({
      title: 'Success',
      text: 'successful login',
      icon: 'success',
      button: 'OK'
    }).then(res=>{
      window.location.href = '/web/pages/accounts.html';
    })})
    .catch(err => swal({
      title: 'Error',
      text: 'error',
      icon: 'error',
      button: 'OK'
  }));
  },
}
})
app.mount("#app")