const {createApp} = Vue;

const app = createApp ({
    data(){
        return{
            loan: {},
            loans: {},
            amount: null,
            payments: null,
            AccountDestiny: "",
            client: [],
            clientAccounts: [],
        }
    },
    created(){
        this.dataAccount();

        this.params = new URLSearchParams(location.search)
        const identificador = this.params.get('id')
        axios.get("http://localhost:8080/api/clients/current")
        .then(res => {
          this.account = res.data
          this.loan = this.account.loans
          this.loans = this.loan.find(loan => loan.id == identificador)
          console.log(this.account)
          console.log(this.loan)
          console.log(this.loans)
        }) 
        .catch(error => console.error(error))
    },
    methods:{
        dataAccount() {
            axios
              .get("http://localhost:8080/api/clients/current")
              .then((res) => {
                this.client = res.data;
                this.clientAccounts = this.client.accounts;
                console.log(this.clientAccounts);
              })
              .catch((err) => {
                console.error(err);
              });
          },
          pay(){
            this.params = new URLSearchParams(location.search)
            const identificador = this.params.get('id')

            axios.post(`/api/loan/pay/${identificador}`,{
                amount: this.amount,
                numberAccountDestiny: this.AccountDestiny,
                payments: this.payments
            })
            .then((res) => {
               swal({
              title: 'Success',
              text: 'loan made',
              icon: 'success',
              button: 'OK'
            }).then(res=>{
              window.location.href = "/web/pages/accounts.html";
            })})
            .catch((err) => {
                swal({
                  title: 'Error',
                  text: 'error',
                  icon: 'error',
                  button: 'OK'
              });
              });
          }
    },
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