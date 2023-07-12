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
  loanCreated() {
    // Obtén el valor del campo de texto
    let amount = document.querySelector('#number').value;

    // Elimina los caracteres no numéricos
    amount = amount.replace(/[^0-9]/g, '');

    // Convierte la cadena a un número
    amount = parseInt(amount);

    axios.post("http://localhost:8080/api/loans", {
      name: this.selectType,
      amount: amount,
      numberAccountDestiny: this.selectAccount,
      payments: this.selectPayments
    })
      .then(res => {
        alert("prestamo realizado")
        window.location.href = "/web/pages/accounts.html"
      })
      .catch(err => {
        console.error(err);
      });
  }
  }
})

app.mount("#app")
