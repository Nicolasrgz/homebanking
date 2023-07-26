const {createApp} = Vue

const app = createApp({
    data(){
        return{
            melba: [],
            melbaAccounts: [],
            melbaSort: [],
            loansSort: [],
            typeAccount: "",
        }
    },
    created(){
        this.user()
    },
    methods: {
      user() {
          axios.get("http://localhost:8080/api/clients/current")
              .then(response => {
                  this.melba = response.data
                  this.melbaAccounts = this.melba.accounts.filter(account => account.active == null)
                  this.melbaSort = this.melbaAccounts.sort((a, b) => a.id - b.id)
                  this.melbaLoans = this.melba.loans
                  this.loansSort = this.melbaLoans.sort((a, b) => a.id - b.id)
                  console.log(this.melba)
                  console.log(this.melbaSort)
                  console.log(this.melbaLoans)
              })
              .catch(err => {
                  swal({
                      title: 'Error',
                      text: 'An error occurred while retrieving user information',
                      icon: 'error',
                      button: 'OK'
                  });
              });
      },
      LogOut() {
          axios.post('/api/logout')
              .then(response => {
                  // Successful logout
                  // Redirect to accounts.html
                  window.location.href = '/web/index.html';
              })
              .catch(error => {
                  // Failed logout
                  // Show error message to user
                  swal({
                      title: 'Error',
                      text: 'Error logging out',
                      icon: 'error',
                      button: 'OK'
                  });
              });
      },
      createdAccount() {
        swal({
            title: "Are you sure?",
            text: "Are you sure you want to create an account?",
            icon: "warning",
            buttons: ["No", "Yes"],
            dangerMode: true,
        })
        .then(()=>{
            axios.post(`/api/clients/current/accounts?type=${this.typeAccount}`)          
            .then(res => {
                swal({
                    title: 'Success',
                    text: 'Account created',
                    icon: 'success',
                    button: 'OK'
                })
                .then(()=>{
                  window.location.href = '/web/pages/accounts.html'
                })
                .catch(err => swal({
                    title: 'Error',
                    text: 'Account limit reached',
                    icon: 'error',
                    button: 'OK'
                }));
            })
        })
      },
      redirection() {
          return window.location.href = "/web/pages/loan-application.html"
      },
      redirectionLoan(){
        return window.location.href = "/web/pages/loan-ap"
      },
      deleteAccount(event) {
        
          // Get the card ID from the data-id attribute of the button
          let accountId = event.target.getAttribute('data-id');
  
          axios.patch(`/api/accounts/${accountId}/deactivate`)
              .then(res => {
                swal({
                  title: 'Success',
                  text: 'Account deleted',
                  icon: 'success',
                  button: 'OK'
              })
              .then(()=>{
                window.location.href = "/web/pages/accounts.html"
              })
                 
              })
              .catch(error => {
                  // Failed login
                  // Show error message to user
                  swal({
                      title: 'Error',
                      text: 'Error deleting account',
                      icon: 'error',
                      button: 'OK'
                  });
              });
      }
  }
  
  
})

app.component('format-currency', {
  props: ['value'],
  template: `{{formattedValue}}`,
  computed: {
    formattedValue() {
      return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
      }).format(this.value);
    }
  }
})

app.mount("#app")
