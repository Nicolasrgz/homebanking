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
  .then(response => {
      if (response.status === 201) {
          swal({
              title: 'Success',
              text: response.data,
              icon: 'success',
              button: 'OK'
          }).then(res => {
              this.submit2()
          });
      }
  })
  .catch(err => {
      if (err.response.status === 403) {
          let errorMessage = err.response.data;
          if (errorMessage === 'the firstname field is incomplete') {
              errorMessage = 'Please enter your first name.';
          } else if (errorMessage === 'the lastname field is incomplete') {
              errorMessage = 'Please enter your last name.';
          } else if (errorMessage === 'the email field is incomplete') {
              errorMessage = 'Please enter your email address.';
          } else if (errorMessage === 'the password field is incomplete') {
              errorMessage = 'Please enter a password.';
          } else if (errorMessage === 'Email already in use') {
              errorMessage = 'The email address you entered is already in use. Please use a different email address.';
          } else if (errorMessage === 'Email does not exist') {
              errorMessage = 'The email address you entered does not exist. Please enter a valid email address.';
          }
          swal({
              title: 'Error',
              text: errorMessage,
              icon: 'error',
              button: 'OK'
          });
      }
  });
},
redirection(){
  return window.location.href = '/web/pages/register.html';
}

}
})
app.mount("#app")