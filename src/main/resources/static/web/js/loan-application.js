const {createApp} = Vue;

const app = createApp({
  data() {
    return {
      selectType: "",
      selectPayments: "",
      selectAccount: "",
      amount: 0,
      loansPayments: {},
      loans: {},
      loansPaymentsMortgage: [],
      loansPaymentsPersonnel: [],
      loansPaymentsAutomotive: [],
      client: [],
      clientAccounts: [],
    }
  },
  created() {
    this.loansData()
    this.dataAccount()
  },
  mounted() {
    new Cleave('#number', {
      numeral: true,
      numeralThousandsGroupStyle: 'thousand',
      prefix: '$'
    });
  },
  methods: {
    loansData() {
      axios.get("http://localhost:8080/api/loans")
        .then(res => {
          this.loans = res.data
          this.loansPaymentsMortgage = this.loans[0].payments
          this.loansPaymentsPersonnel = this.loans[1].payments
          this.loansPaymentsAutomotive = this.loans[2].payments
          console.log(this.loans)
          console.log(this.loansPaymentsMortgage, this.loansPaymentsPersonnel, this.loansPaymentsAutomotive)
        })
        .catch(err => {
          console.error(err);
        });
    },
    dataAccount() {
      axios.get("http://localhost:8080/api/clients/current")
        .then(res => {
          this.client = res.data
          this.clientAccounts = this.client.accounts
          console.log(this.clientAccounts)
        })
        .catch(err => {
          console.error(err);
        });
    },
    loanCreated(){
        axios.post("http://localhost:8080/api/loans")
        .then(res => {

        })
        .catch(err => {
            console.error(err);
          });
    }
  }
})

app.mount("#app")
